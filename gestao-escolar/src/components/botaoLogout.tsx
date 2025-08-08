import React from 'react';
import { Button, View } from 'react-native';
import { useAuth } from '../contexts/autenticacaoContext';

export default function BotaoLogout(){
    const {logout} = useAuth();

    return(
        <View
            style={{
                margin:20,
                marginTop:40,
            }}>
            <Button title='Sair da Conta'  onPress={logout} color={'#1E5F8C'} 
            />
        </View>
    )
}

