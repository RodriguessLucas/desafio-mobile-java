import { useState } from "react";
import { Alert, Image, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";

export default function Login(){
    const [usuario, setUsuario] = useState('');
    const [senha, setSenha] = useState('');

    const ajuda = [
        'Neste campo insira o login, por exemplo: lucas@gmail.com',
        'Neste campo insira a senha, por exemplo A123456',
        'Este botão ao ser clicado, efetuará o login \ncaso os dados estejam corretos, logará \ncaso contrário dará erro',
    ];

    const handleAjuda = (valor) => {
        Alert.alert('Ajuda', ajuda[valor]);
    }

    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <Image source={require('../../assets/images/logo.png')} style={styles.logo} />
                <Text style={styles.nomeEscola}>Escola x</Text>
            </View>

            <Text style={styles.login}>LOGIN</Text>

            <Text style={styles.label}>Usuário</Text>
            <View style={styles.inputRow}>
                <TextInput 
                    style={styles.input} 
                    placeholder="exemplo: lucas@gmail.com"
                    placeholderTextColor="#aaa"
                    value={usuario}
                    onChangeText={setUsuario}
                />
                <TouchableOpacity 
                    style={styles.btnAjudaContainer} 
                    onPress={() => handleAjuda(0)}>
                    <Image source={require('../../assets/images/btnajuda.png')} style= {styles.btnAjudaImage}/>
                    
                </TouchableOpacity>
            </View>


            <Text style={styles.label}>Senha</Text>
            <View style={styles.inputRow}>
                <TextInput 
                    style={styles.input} 
                    placeholder="exemplo: A123456"
                    placeholderTextColor="#aaa"
                    value={senha}
                    onChangeText={setSenha}
                />
                <TouchableOpacity 
                    style={styles.btnAjudaContainer} 
                    onPress={() => handleAjuda(1)}>
                    <Image source={require('../../assets/images/btnajuda.png')} style= {styles.btnAjudaImage}/>
                    
                </TouchableOpacity>

            </View>


            <View>

                <TouchableOpacity 
                    style={styles.button} 
                    onPress={() => handleAjuda(1)}>

                    <Text style={styles.textoBtnLogar}>Entrar</Text>
                </TouchableOpacity>

            </View>

        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingHorizontal: 30,
        justifyContent: 'center',
        backgroundColor: '#F4F6F8',
        marginBottom:60,
    },
    header: {
        alignItems: 'center',
        justifyContent: 'center',
        marginBottom: 40,
        flexDirection: 'row'
    },
    logo: {
        width: 80,
        height: 80,
        marginRight: 20,
        resizeMode: 'contain'
    },
    nomeEscola: {
        fontSize: 22,
        color: '#333333',
        textAlign: 'center',
        fontWeight: '800'
    },
    login: {
        fontSize: 25,
        fontWeight: 'bold',
        fontStyle: 'italic',
        color: '#333333',
        textAlign: 'center',
        marginBottom: 30,
    },
    label: {
        fontSize: 15,
        color: '#555',
        marginBottom: 5,
        marginLeft: 5,
    },
    inputRow: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 20,
    },
    input: {
        flex: 1,
        height: 50,
        backgroundColor: '#fff',
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 25,
        paddingHorizontal: 15,
        fontSize: 16,
        color: '#333',
    },
    btnAjudaContainer: {
        marginLeft: 5,
        width: 40,
        height: 40,
        justifyContent: 'center',
        alignItems: 'center'
    },
    btnAjudaImage: {
        width: '100%',
        height: '100%',
        resizeMode: 'contain'
    },
    button: {
        backgroundColor: '#1E5F8C',
        borderRadius: 25,
        height: 50,
        justifyContent: 'center',
        marginTop: 20,
    },
    textoBtnLogar: {
        color: '#333333',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center',
    }
});