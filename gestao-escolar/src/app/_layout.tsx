import { Stack, useRouter } from "expo-router";
import { useEffect } from "react";
import { ProverAutenticacao, useAuth } from '../contexts/autenticacaoContext';

const LayoutInicial = () => {
    const { estadoAut } = useAuth();
    const router = useRouter();

    useEffect(() => {
        if (estadoAut.autenticado === null) {
            return;
        }

        if (estadoAut.autenticado) {
            const papel = estadoAut.papel;


            if(papel === 'ADMIN'){
                router.replace('/home/admin/dashboard');
            }
            else if (papel === 'DIRETOR') {
                router.replace('/home/diretor/dashboard');
            } 
            else if (papel === 'PROFESSOR') {
                router.replace('/home/professor/dashboard');
            } 
            else if (papel === 'ALUNO') {
                router.replace('/home/aluno/dashboard');
            }
        } 
        else {
            router.replace('/autenticacao/login');
        }

    }, [estadoAut.autenticado]);

    return (
        <Stack screenOptions={{ headerShown: false }}>
            <Stack.Screen name="autenticacao/login" />
            <Stack.Screen name="home/diretor/dashboard" />
            <Stack.Screen name="home/professor/dashboard" />
            <Stack.Screen name="home/aluno/dashboard" />
            <Stack.Screen name="home/admin/dashboard" />
        </Stack>
    );
}

export default function RootLayout() {
    return (
        <ProverAutenticacao>
            <LayoutInicial />
        </ProverAutenticacao>
    )
}