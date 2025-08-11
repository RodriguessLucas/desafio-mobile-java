import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, Alert, ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

import BotaoLogout from '../../../components/botaoLogout';
import Cabecalho from '../../../components/cabecalho';


export default function DiretorDashboard() {
  const [viewAtiva, setViewAtiva] = useState('professores');
  const [professores, setProfessores] = useState([]);
  const [turmas, setTurmas] = useState([]);
  const [alunos, setAlunos] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [mostrarNotas, setMostrarNotas] = useState(false);

  useEffect(() => {
    const buscarDados = async () => {
      setIsLoading(true);
      try {
        const [profResponse, turmasResponse, alunosResponse] = await Promise.all([
          axios.get('https://gestao-escolar-m4yq.onrender.com/api/diretor/professores'),
          axios.get('https://gestao-escolar-m4yq.onrender.com/api/turmas'), 
          axios.get('https://gestao-escolar-m4yq.onrender.com/api/diretor/alunos'),
        ]);

        setProfessores(profResponse.data || []);
        setTurmas(turmasResponse.data || []);
        setAlunos(alunosResponse.data || []);

      } catch (error) {
        console.error("Erro ao buscar dados:", error);
        Alert.alert('Erro de Rede', 'Não foi possível carregar os dados do servidor.');
      } finally {
        setIsLoading(false);
      }
    };
    buscarDados();
  }, []);


  const RenderizarConteudo = () => {
    if (isLoading) {
      return <ActivityIndicator size="large" color="#1E5F8C" style={{ marginTop: 40 }}/>;
    }

    if (viewAtiva === 'professores') {
      return (
        <View>
          <Text style={styles.listHeader}>Todos os Professores</Text>
          {professores.map(prof => (
            <View key={prof.id} style={styles.listItem}>
              <Text style={styles.listItemTitle}>{prof.nome}</Text>
              <Text style={styles.listItemSubtitle}>Matérias: {prof.materias.join(', ')}</Text>
            </View>
          ))}
        </View>
      );
    }

    if (viewAtiva === 'turmas') {
      return (
        <View>
          <Text style={styles.listHeader}>Todas as Turmas</Text>
          {turmas.map(turma => (
            <View key={turma.id} style={styles.listItem}>
              <Text style={styles.listItemTitle}>{turma.turma}</Text>
              <Text style={styles.listItemSubtitle}>Turno: {turma.turno}</Text>
            </View>
          ))}
        </View>
      );
    }

    if (viewAtiva === 'alunos') {
      return (
        <View>
          <View style={styles.listHeaderContainer}>
            <Text style={styles.listHeader}>Alunos e Notas Gerais</Text>
            <TouchableOpacity onPress={() => setMostrarNotas(!mostrarNotas)}>
              <Text style={styles.toggleNotasTexto}>{mostrarNotas ? 'Ocultar Notas' : 'Mostrar Notas'}</Text>
            </TouchableOpacity>
          </View>

          {alunos.map((aluno, index) => (
            <View key={`${aluno.id}-${index}`} style={styles.listItem}>
              <Text style={styles.listItemTitle}>{aluno.nome}</Text>
              
              {mostrarNotas && (
                <View style={styles.notasContainer}>
                  {aluno.notas.map((nota, notaIndex) => (
                    <View key={`${nota.idMateria}-${notaIndex}`} style={styles.notaIndividual}>
                      <Text style={styles.notaMateria}>{nota.nomeMateria}:</Text>
                      <Text style={styles.notaValor}>{nota.nota.toFixed(1)}</Text>
                    </View>
                  ))}
                </View>
              )}
            </View>
          ))}
        </View>
      );
    }

    if (viewAtiva === 'cadastrar') {
      return (
        <View>
          <Text style={styles.listHeader}>Área de Cadastro</Text>
          <Text>Aqui ficará o formulário de cadastro.</Text>
        </View>
      );
    }
    
    return null;
  };

  return (
    <View style={styles.container}>
      <Cabecalho />
      
      <View style={styles.botoesAcaoContainer}>
        <TouchableOpacity 
          style={[styles.botaoAcao, viewAtiva === 'professores' && styles.botaoAtivo]} 
          onPress={() => setViewAtiva('professores')}>
          <Text style={[styles.botaoAcaoTexto, viewAtiva === 'professores' && styles.textoAtivo]}>Professores</Text>
        </TouchableOpacity>
        
        <TouchableOpacity 
          style={[styles.botaoAcao, viewAtiva === 'turmas' && styles.botaoAtivo]} 
          onPress={() => setViewAtiva('turmas')}>
          <Text style={[styles.botaoAcaoTexto, viewAtiva === 'turmas' && styles.textoAtivo]}>Turmas</Text>
        </TouchableOpacity>

        <TouchableOpacity 
          style={[styles.botaoAcao, viewAtiva === 'alunos' && styles.botaoAtivo]} 
          onPress={() => setViewAtiva('alunos')}>
          <Text style={[styles.botaoAcaoTexto, viewAtiva === 'alunos' && styles.textoAtivo]}>Alunos</Text>
        </TouchableOpacity>

        <TouchableOpacity 
          style={[styles.botaoAcao, viewAtiva === 'cadastrar' && styles.botaoAtivo]} 
          onPress={() => setViewAtiva('cadastrar')}>
          <Text style={[styles.botaoAcaoTexto, viewAtiva === 'cadastrar' && styles.textoAtivo]}>Cadastrar</Text>
        </TouchableOpacity>
      </View>

      <ScrollView style={styles.conteudoScroll}>
        <View style={styles.conteudoContainer}>
          <RenderizarConteudo />
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
  botoesAcaoContainer: { 
    flexDirection: 'row', 
    justifyContent: 'space-around', 
    marginVertical: 15, 
    paddingHorizontal: 10,
    flexWrap: 'wrap', 
  },
  botaoAcao: { 
    paddingVertical: 10, 
    paddingHorizontal: 15, 
    borderRadius: 20, 
    backgroundColor: '#fff', 
    elevation: 2, 
    borderWidth: 1, 
    borderColor: '#ddd',
    marginBottom: 10,
  },
  botaoAtivo: { 
    backgroundColor: '#1E5F8C', 
    borderColor: '#1E5F8C',
  },
  botaoAcaoTexto: { 
    fontWeight: '600', 
    color: '#1E5F8C',
  },
  textoAtivo: { 
    color: '#fff',
  },
  conteudoScroll: {
    flex: 1,
  },
  conteudoContainer: { 
    paddingHorizontal: 15,
    paddingBottom: 20,
  },
  listHeaderContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 15,
  },
  listHeader: { 
    fontSize: 20, 
    fontWeight: 'bold', 
    color: '#333', 
  },
  toggleNotasTexto: {
    fontSize: 14,
    fontWeight: '600',
    color: '#1E5F8C',
  },
  listItem: { 
    backgroundColor: '#fff', 
    padding: 15, 
    borderRadius: 8, 
    marginBottom: 10, 
    elevation: 1, 
    borderWidth: 1, 
    borderColor: '#eee',
  },
  listItemTitle: { 
    fontSize: 18, 
    fontWeight: 'bold', 
    color: '#212121',
  },
  listItemSubtitle: { 
    fontSize: 14, 
    color: 'gray', 
    marginTop: 4,
  },
  notasContainer: {
    marginTop: 10,
    borderTopWidth: 1,
    borderTopColor: '#eee',
    paddingTop: 10,
  },
  notaIndividual: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 5,
  },
  notaMateria: {
    fontSize: 14,
    color: '#555',
  },
  notaValor: {
    fontSize: 14,
    fontWeight: 'bold',
  },
});
