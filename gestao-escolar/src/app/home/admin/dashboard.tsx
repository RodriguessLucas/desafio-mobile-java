import { StyleSheet, Text, View } from 'react-native';
import BotaoLogout from '../../../components/botaoLogout';
import Cabecalho from '../../../components/cabecalho';

export default function dashboard(){
    return(

        <View style={styles.container}>
            <Cabecalho/>
            
            <Text>Entrou no dashboard de admin</Text>
            <BotaoLogout/>

        </View>
    )
}


const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    backgroundColor: '#F4F6F8',
    paddingVertical:30,
  },
});
