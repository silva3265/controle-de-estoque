package br.com.estoque.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.estoque.connection.Conexao;
import br.com.estoque.entities.Produto;

public class ProdutoDao {

	public Produto getProdutoByNome(String nome) throws SQLException {
		String sql = "SELECT * FROM produto WHERE nome = ? ";
		
		Connection conexao = new Conexao().getConnection(); 
		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		
		stmt.setString(1, nome); /* Essa função esta substituindo o nosso coringa da query nome = '?'*/
		
		ResultSet resultSet = stmt.executeQuery(); /* resultSet - Representa uma tabela do banco de dados, ele aponta para o cabeçalho da tabela*/
		
		 Produto produto = null;
		// resultSet - ele vai retornar verdadeiro se ele existir
		// Ele vai retornar apenas o primeiro objeto 
		if (resultSet.next()) { /* next() - informa se existe um proximo Objeto (Registro), proxima coluna */
			produto = new Produto(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getBigDecimal("valor"), resultSet.getInt("quantidade"));
		}
		conexao.close();
		
		return produto;
		
	}

	/* AS TRES PRINCIPAIS INSTRUÇÕES DE DENTRO DE UM METODO DA DAO SÃO: SQL (QUERY), CONEXÃO, E CHAMADA DA CONEXÃO */
	public void register(Produto produto) throws SQLException {
		
		/* QUERY SQL */
		String sql = " INSERT INTO produto (nome, descricao, valor, quantidade) VALUES (?, ?, ?, ? )";
		
		Connection connection =  new Conexao().getConnection();
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(1, produto.getNome()); /* o indice '1' é o nosso primeiro coringa '?' */
		stmt.setString(2, produto.getDescricao()); /* o indice '2' éo nosso segundo coringa '?' */
		stmt.setBigDecimal(3, produto.getValor());
		stmt.setInt(4, produto.getQuantidade());
		
		stmt.execute();
		stmt.close();
		connection.close();
	}

	public void update(Produto produto) {
		
	}

	public ArrayList<Produto> getProdutos() throws SQLException {
		
		/* o SELECT sempre nos retorna um ResultSet */
		String sql = "SELECT * FROM produto"; /* Vai retornar todos os registros da tabela */
		
		Connection connection =  new Conexao().getConnection();
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		ResultSet resultSet = stmt.executeQuery(); /* Executamos a nossa Query*/
	
		ArrayList<Produto> allProdutos = new ArrayList(); /* Criamos a lista para retonar todos os Produtos do tipo 'Produto'*/
		
		// new BigDecimal(5.50) - ele é um objeto, então temos que instancialo! se nao instanciarmos ele identifica como 'double'
//		allProdutos.set(0, new Produto(1 , "Feijão", " Grao ", new BigDecimal(5.50) , 25)); /* 'set' - Atualização dentro do Vetor*/
		
		while (resultSet.next()) { /* Cada vez que o while rodar nos vamos criar um produto novo e adicionar ele na lista */
			Produto produto = new Produto(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getBigDecimal("valor"), resultSet.getInt("quantidade"));
			
			allProdutos.add(produto); /* Cada produto achado atraves do while ele vai adicionar na nossa Lista 'allProdutos' */
		
		}
		
		connection.close(); /* Fechando a conexão */
		
		return allProdutos; /* no final retorna todos os produtos */
	}

	public void updateNome(String nome1, String nome2) throws SQLException {
		String sql = "UPDATE produto SET nome = ? WHERE nome = ? "; /* Precisamos saber qual produto queremos atualizar, por isso a clausula*/
		
		Connection conexao = new Conexao().getConnection();
		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		
		/* NÃO PRECISAMOS INSTANCIAR O RESULTSET PORQUE SÓ USAMOS APAENAS PARA CONSULTA 'SELECT' */
		stmt.setString(1, nome2); /* 'nome2' é o nome que vai entrar no lugar do 'nome1' */
		stmt.setString(2, nome1);
		stmt.execute();
		stmt.close();
		conexao.close();
	}

	public void updateDescricao(String descricaoProduto) {
		// TODO Auto-generated method stub
		
	}

	public void updateQuantidade(Integer quantidadeProduto) {
		// TODO Auto-generated method stub
		
	}

	public void updateValor(BigDecimal valorProduto) {
		// TODO Auto-generated method stub
		
	}
}










