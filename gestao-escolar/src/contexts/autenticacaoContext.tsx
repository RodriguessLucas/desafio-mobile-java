import axios from 'axios';
import { useRouter } from 'expo-router';
import * as SecureStore from 'expo-secure-store';
import React, { createContext, useContext, useEffect, useState } from "react";

const API_BASE_URL = 'https://gestao-escolar-m4yq.onrender.com';
const API_URL = `${API_BASE_URL}/api`;

interface DadosAutenticacao {
    estadoAut: {
        token: string | null;
        autenticado: boolean | null;
        usuario: { login: string } | null;
        nome: string | null;
        papel: 'DIRETOR' | 'PROFESSOR' | 'ALUNO' | 'ADMIN' | null;
        id: string | null;
    };
    login: (login: string, senha: string) => Promise<void>;
    logout: () => void;
}

const AuthContext = createContext<DadosAutenticacao>({} as DadosAutenticacao);

export function ProverAutenticacao({ children }: { children: React.ReactNode }) {
    const [estadoAut, setEstadoAut] = useState<DadosAutenticacao['estadoAut']>({
        token: null,
        autenticado: null,
        usuario: null,
        nome: null,
        papel: null,
        id: null, 
    });
    const router = useRouter();

    useEffect(() => {
    const loadToken = async () => {
        const token = await SecureStore.getItemAsync('authToken');
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            try {
            const response = await axios.get(`${API_URL}/user/me`);
                setEstadoAut({
                token: token,
                autenticado: true,
                usuario: response.data.user,
                nome: response.data.name,
                papel: response.data.user.role,
                id: response.data.user.id, 
            });
            } catch (e) {
                await logout();
            }
        } 
        else {
            setEstadoAut({ token: null, autenticado: false, usuario: null,nome: null, papel: null, id:null });
        }
        };
        loadToken();
    }, []);

    const login = async (login: string, senha: string) => {
        try {
            const response = await axios.post(`${API_URL}/auth/login`, { login, senha });
            
            const { token, papel, email, nome, id } = response.data;

            setEstadoAut({
                token: token,
                autenticado: true,
                usuario: { login: email },
                nome: nome,
                papel: papel,
                id: id 
            });

            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            await SecureStore.setItemAsync('authToken', token);

        } catch (error: any) { 
            console.error('Erro no login:', error.response?.data || error.message);
            throw new Error(error.response?.data?.message || "Credenciais inválidas.");
        }
    };

    const logout = async () => {
        await SecureStore.deleteItemAsync('authToken');
        delete axios.defaults.headers.common['Authorization'];
        setEstadoAut({ token: null, autenticado: false, usuario: null, nome: null, papel: null , id: null});
        router.replace('/autenticacao/login'); 
    };

    return (
        <AuthContext.Provider value={{ estadoAut, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
