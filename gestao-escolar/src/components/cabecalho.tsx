import React from 'react';
import { Image, StyleSheet, Text, View } from 'react-native';
import { useAuth } from '../contexts/autenticacaoContext';


export default function Cabecalho() {
  const { estadoAut } = useAuth();

  return (
    <View style={styles.header}>
      <View style={styles.headerEsq}>
        <Image source={require('../assets/images/logo.png')} style={styles.logo} />
        <Text style={styles.nomeEscola}>Centro Educacional{'\n'}Passarinho Azul</Text>
      </View>
      <View style={styles.headerDir}>
        <Text style={styles.nomeUsu}>{estadoAut.nome || 'Usuário'}</Text>
        <Image source={require('../assets/images/perfil.png')} style={styles.perfil} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  header: {
    width: '100%', 
    flexDirection: 'row',
    justifyContent: 'space-between', 
    alignItems: 'center',
    paddingVertical: 10,
    paddingHorizontal: 15,
    backgroundColor: '#F4F6F8',
    marginBottom: 20, 
  },
  headerEsq: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  logo: {
    width: 50,
    height: 50,
    marginRight: 10,
    resizeMode:'contain'
  },
  nomeEscola: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#000000',
  },
  headerDir: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  nomeUsu: {
    marginRight: 10,
    fontSize: 16,
    color: '#333333',
    fontWeight: '500',
  },
  perfil: {
    width: 45,
    height: 45,
    borderRadius: 22.5, 
    backgroundColor: '#FBC02D', 
    borderWidth: 2,
    borderColor: '#fff',
    elevation: 3,
  },
});