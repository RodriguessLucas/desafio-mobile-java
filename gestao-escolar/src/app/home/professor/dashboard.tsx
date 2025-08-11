import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, Alert, ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

import BotaoLogout from '../../../components/botaoLogout';
import Cabecalho from '../../../components/cabecalho';
import { useAuth } from '../../../contexts/autenticacaoContext';

const API_URL = 'https://gestao-escolar-m4yq.onrender.com';

export default function ProfessorDashboard() {

  const { estadoAut } = useAuth(); 
  const [turmas, setTurmas] = useState([]);
  const [turmaSelecionada, setTurmaSelecionada] = useState(null);
  const [alunos, setAlunos] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isLoadingAlunos, setIsLoadingAlunos] = useState(false);

  useEffect(() => {
    const buscarMinhasTurmas = async () => {
      const idProfessor = estadoAut.id;
      if (!idProfessor) return; 

      setIsLoading(true);
      try {
        const response = await axios.get(`${API_URL}/api/turmas/${idProfessor}/professor`);
        setTurmas(response.data || []);
      } catch (error) {
        Alert.alert('Erro', 'Não foi possível carregar suas turmas.');
      } finally {
        setIsLoading(false);
      }
    };
    buscarMinhasTurmas();
  }, [estadoAut.id]); 

  const selecionarTurma = async (turma) => {
    setTurmaSelecionada(turma);
    setIsLoadingAlunos(true);
    try {
        const response = await axios.get(`${API_URL}/api/turmas/${turma.id}/alunos`);
        setAlunos(response.data || []);
    } catch (error) {
        Alert.alert('Erro', `Não foi possível carregar os alunos da turma ${turma.turma}.`);
        setAlunos([]);
    } finally {
        setIsLoadingAlunos(false);
    }
  };

  const voltarParaTurmas = () => {
    setTurmaSelecionada(null);
    setAlunos([]);
  };


  if (turmaSelecionada) {
    return (
      <View style={styles.container}>
        <Cabecalho />
        <ScrollView style={styles.conteudoScroll}>
            <View style={styles.conteudoContainer}>
                <TouchableOpacity onPress={voltarParaTurmas} style={styles.botaoVoltar}>
                    <Text style={styles.botaoVoltarTexto}>{"< Ver todas as turmas"}</Text>
                </TouchableOpacity>
                <Text style={styles.listHeader}>Alunos da {turmaSelecionada.turma}</Text>
                {isLoadingAlunos ? (
                    <ActivityIndicator size="large" color="#1E5F8C" />
                ) : (
                    alunos.map((aluno, index) => (
                        <View key={`${aluno.id}-${index}`} style={styles.listItem}>
                            <Text style={styles.listItemTitle}>{aluno.nome}</Text>
                        </View>
                    ))
                )}
            </View>
            <BotaoLogout />
        </ScrollView>
      </View>
    );
  }


  return (
    <View style={styles.container}>
      <Cabecalho />
      <ScrollView style={styles.conteudoScroll}>
        <View style={styles.conteudoContainer}>
            <Text style={styles.listHeader}>Minhas Turmas</Text>
            {isLoading ? (
                <ActivityIndicator size="large" color="#1E5F8C" />
            ) : (
                turmas.map((turma, index) => (
                <TouchableOpacity key={`${turma.id}-${index}`} style={styles.listItem} onPress={() => selecionarTurma(turma)}>
                    <Text style={styles.listItemTitle}>{turma.turma}</Text>
                    <Text style={styles.listItemSubtitle}>Turno: {turma.turno}</Text>
                </TouchableOpacity>
                ))
            )}
        </View>
        <BotaoLogout />
      </ScrollView>
    </View>
  );
}


const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    backgroundColor: '#F4F6F8',
    paddingVertical:30,
  },
  conteudoScroll: {
    flex: 1,
  },
  conteudoContainer: { 
    padding: 15,
  },
  listHeader: { 
    fontSize: 22, 
    fontWeight: 'bold', 
    color: '#333', 
    marginBottom: 15,
  },
  listItem: { 
    backgroundColor: '#fff', 
    padding: 20, 
    borderRadius: 8, 
    marginBottom: 10, 
    elevation: 2, 
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  listItemTitle: { 
    fontSize: 18, 
    fontWeight: 'bold', 
    color: '#212121',
  },
  listItemSubtitle: { 
    fontSize: 14, 
    color: 'gray', 
  },
  botaoVoltar: {
    marginBottom: 20,
  },
  botaoVoltarTexto: {
    fontSize: 16,
    color: '#1E5F8C',
    fontWeight: '600',
  },
});
