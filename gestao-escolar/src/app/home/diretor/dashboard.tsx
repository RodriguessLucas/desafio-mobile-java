import React, { useEffect, useState } from 'react';
import { ActivityIndicator, Alert, ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';


import BotaoLogout from '../../../components/botaoLogout';
import Cabecalho from '../../../components/cabecalho';

const mockProfessores = [
  { id: 'p1', nome: 'Ana Carolina', disciplina: 'Matemática' },
  { id: 'p2', nome: 'Bruno Costa', disciplina: 'Português' },
  { id: 'p3', nome: 'Carlos Dias', disciplina: 'História' },
];
const mockTurmas = [
  { id: 't1', nome: 'Turma 6º Ano A', periodo: 'Manhã', totalAlunos: 25 },
  { id: 't2', nome: 'Turma 7º Ano B', periodo: 'Tarde', totalAlunos: 22 },
];
const mockAlunos = [
  { id: 'a1', nome: 'Lucas Rodrigues', turma: 'Turma 6º Ano A', mediaGeral: 8.5 },
  { id: 'a2', nome: 'Maria Eduarda', turma: 'Turma 7º Ano B', mediaGeral: 9.1 },
  { id: 'a3', nome: 'João Vitor', turma: 'Turma 6º Ano A', mediaGeral: 7.2 },
];

export default function DiretorDashboard() {
  const [viewAtiva, setViewAtiva] = useState('professores');
  const [professores, setProfessores] = useState([]);
  const [turmas, setTurmas] = useState([]);
  const [alunos, setAlunos] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const buscarDados = async () => {
      setIsLoading(true);
      try {
        setProfessores(mockProfessores);
        setTurmas(mockTurmas);
        setAlunos(mockAlunos);
      } catch (error) {
        Alert.alert('Erro de Rede', 'Não foi possível carregar os dados do servidor.');
      } finally {
        setIsLoading(false);
      }
    };
    buscarDados();
  }, []);

  const RenderizarConteudo = () => {
    if (isLoading) {
      return <ActivityIndicator size="large" color="#0d47a1" style={{ marginTop: 40 }}/>;
    }

    if (viewAtiva === 'professores') {
      return (
        <View>
          <Text style={styles.listHeader}>Todos os Professores</Text>
          {professores.map(prof => (
            <View key={prof.id} style={styles.listItem}>
              <Text style={styles.listItemTitle}>{prof.nome}</Text>
              <Text style={styles.listItemSubtitle}>Disciplina Principal: {prof.disciplina}</Text>
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
              <Text style={styles.listItemTitle}>{turma.nome}</Text>
              <Text style={styles.listItemSubtitle}>{turma.periodo} - {turma.totalAlunos} alunos</Text>
            </View>
          ))}
        </View>
      );
    }

    if (viewAtiva === 'alunos') {
      return (
        <View>
          <Text style={styles.listHeader}>Alunos e Notas Gerais</Text>
          {alunos.map(aluno => (
            <View key={aluno.id} style={styles.listItem}>
              <Text style={styles.listItemTitle}>{aluno.nome}</Text>
              <View style={styles.alunoDetails}>
                <Text style={styles.listItemSubtitle}>Turma: {aluno.turma}</Text>
                <Text style={styles.mediaNota}>Média: {aluno.mediaGeral.toFixed(1)}</Text>
              </View>
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
    <ScrollView style={styles.container}>
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

      <View style={styles.conteudoContainer}>
        <RenderizarConteudo />
      </View>

      <BotaoLogout />
    </ScrollView>
  );
}


const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    backgroundColor: '#F4F6F8',
    paddingVertical:40,
  },
  botoesAcaoContainer: { 
    flexDirection: 'row', 
    justifyContent: 'space-around', 
    marginVertical: 20, 
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
  conteudoContainer: { 
    paddingHorizontal: 15,
  },
  listHeader: { 
    fontSize: 20, 
    fontWeight: 'bold', 
    color: '#333', 
    marginBottom: 15,
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
  alunoDetails: { 
    flexDirection: 'row', 
    justifyContent: 'space-between', 
    marginTop: 4,
  },
  mediaNota: { 
    fontSize: 14, 
    fontWeight: 'bold', 
    color: '#1E5F8C',
  },
});
