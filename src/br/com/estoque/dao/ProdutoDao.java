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

	public Produto getProdutoByNome(String nome) {
		String sql = "SELECT * FROM produto WHERE nome = ? ";
		
		Connection conexao;
		PreparedStatement stmt;
		Produto produto = null;
		try {
			conexao = new Conexao().getConnection();
			stmt = conexao.prepareStatement(sql);
			
			stmt.setString(1, nome); /* Essa função esta substituindo o nosso coringa da query nome = '?'*/
			
			ResultSet resultSet = stmt.executeQuery(); /* resultSet - Representa uma tabela do banco de dados, ele aponta para o cabeçalho da tabela*/
			
			 
			// resultSet - ele vai retornar verdadeiro se ele existir
			// Ele vai retornar apenas o primeiro objeto 
			if (resultSet.next()) { /* next() - informa se existe um proximo Objeto (Registro), proxima coluna */
				produto = new Produto(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getBigDecimal("valor"), resultSet.getInt("quantidade"));
			}
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return produto;
		
	}
	
	public Produto getProdutoById(Integer id){
		String sql = "SELECT * FROM produto WHERE id = ? ";
		
		Connection conexao = null;
		PreparedStatement stmt; 
		Produto produto = null;
		try {
			conexao = new Conexao().getConnection();
			stmt = conexao.prepareStatement(sql);
			
			stmt.setInt(1, id); /* Essa função esta substituindo o nosso coringa da query nome = '?'*/
			
			ResultSet resultSet = stmt.executeQuery(); /* resultSet - Representa uma tabela do banco de dados, ele aponta para o cabeçalho da tabela*/
			
			// resultSet - ele vai retornar verdadeiro se ele existir
			// Ele vai retornar apenas o primeiro objeto 
			if (resultSet.next()) { /* next() - informa se existe um proximo Objeto (Registro), proxima coluna */
				produto = new Produto(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getBigDecimal("valor"), resultSet.getInt("quantidade"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return produto;
		
	}
	

	/* AS TRES PRINCIPAIS INSTRUÇÕES DE DENTRO DE UM METODO DA DAO SÃO: SQL (QUERY), CONEXÃO, E CHAMADA DA CONEXÃO */
	public void register(Produto produto) {
		
		/* QUERY SQL */
		String sql = " INSERT INTO produto (nome, descricao, valor, quantidade) VALUES (?, ?, ?, ? )";
		
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new Conexao().getConnection();
			connection.setAutoCommit(false); /* só vai fazer o commit quando a gente disser pra fazer, por isso iniciamos com 'false'*/
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, produto.getNome()); /* o indice '1' é o nosso primeiro coringa '?' */
			stmt.setString(2, produto.getDescricao()); /* o indice '2' éo nosso segundo coringa '?' */
			stmt.setBigDecimal(3, produto.getValor());
			stmt.setInt(4, produto.getQuantidade());
			
			stmt.execute();
			connection.commit(); /* se chegou no execute e não der exception, ele faz o commit 'salve as informaçoes'*/
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback(); /* rollback - voltar a versão anterior caso caia no 'catch'*/
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			
			try {
				connection.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Produto> getProdutos() {
		
		/* o SELECT sempre nos retorna um ResultSet */
		String sql = "SELECT * FROM produto"; /* Vai retornar todos os registros da tabela */
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ArrayList<Produto> allProdutos = new ArrayList(); /* Criamos a lista para retonar todos os Produtos do tipo 'Produto'*/
		try {
			connection = new Conexao().getConnection();
			stmt = connection.prepareStatement(sql);
			
			ResultSet resultSet = stmt.executeQuery(); /* Executamos a nossa Query*/
			
			
			// new BigDecimal(5.50) - ele é um objeto, então temos que instancialo! se nao instanciarmos ele identifica como 'double'
//			allProdutos.set(0, new Produto(1 , "Feijão", " Grao ", new BigDecimal(5.50) , 25)); /* 'set' - Atualização dentro do Vetor*/
			
			while (resultSet.next()) { /* Cada vez que o while rodar nos vamos criar um produto novo e adicionar ele na lista */
				Produto produto = new Produto(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getBigDecimal("valor"), resultSet.getInt("quantidade"));
				
				allProdutos.add(produto); /* Cada produto achado atraves do while ele vai adicionar na nossa Lista 'allProdutos' */
			
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /* Fechando a conexão */
			
			
		}
		return allProdutos; /* no final retorna todos os produtos */
		
	}

	public void updateNome(String nome1, String nome2) { /* Pode Lançar uma exceção */
		String sql = "UPDATE produto SET nome = ? WHERE nome = ? "; /* Precisamos saber qual produto queremos atualizar, por isso a clausula*/
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		try {
			conexao = new Conexao().getConnection();
			conexao.setAutoCommit(false);
			stmt = conexao.prepareStatement(sql);
			
			/* NÃO PRECISAMOS INSTANCIAR O RESULTSET PORQUE SÓ USAMOS APENAS PARA CONSULTA 'SELECT' */
			stmt.setString(1, nome2); /* 'nome2' é o nome que vai entrar no lugar do 'nome1' */
			stmt.setString(2, nome1);
			stmt.execute();
			conexao.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); /* Vai printar a Exceção */
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} /* rollback - voltar a versão anterior caso caia no 'catch'*/
		}
		
		finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void updateDescricao(String descricaoProduto, String nome) {
		String sql = "UPDATE produto SET descricao = ? WHERE nome = ? "; /* Precisamos saber qual produto queremos atualizar, por isso a clausula*/
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		try {
			conexao = new Conexao().getConnection();
			conexao.setAutoCommit(false);
			stmt = conexao.prepareStatement(sql);
			
			/* NÃO PRECISAMOS INSTANCIAR O RESULTSET PORQUE SÓ USAMOS APENAS PARA CONSULTA 'SELECT' */
			stmt.setString(1, descricaoProduto); /* 'nome2' é o nome que vai entrar no lugar do 'nome1' */
			stmt.setString(2, nome);
			stmt.execute();
			conexao.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	public void updateValor(BigDecimal valor, String nome) {
		String sql = "UPDATE produto SET valor = ? WHERE nome = ? "; /* Precisamos saber qual produto queremos atualizar, por isso a clausula*/
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		try {
			conexao = new Conexao().getConnection();
			conexao.setAutoCommit(false);
			stmt = conexao.prepareStatement(sql);
			
			/* NÃO PRECISAMOS INSTANCIAR O RESULTSET PORQUE SÓ USAMOS APENAS PARA CONSULTA 'SELECT' */
			stmt.setBigDecimal(1, valor); /* 'nome2' é o nome que vai entrar no lugar do 'nome1' */
			stmt.setString(2, nome);
			stmt.execute();
			conexao.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void updateQuantidade(Integer quantidade, String nome) {
		String sql = "UPDATE produto SET quantidade = ? WHERE nome = ? "; /* Precisamos saber qual produto queremos atualizar, por isso a clausula*/
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		try {
			conexao = new Conexao().getConnection();
			conexao.setAutoCommit(false);
			stmt = conexao.prepareStatement(sql);
			
			/* NÃO PRECISAMOS INSTANCIAR O RESULTSET PORQUE SÓ USAMOS APENAS PARA CONSULTA 'SELECT' */
			stmt.setInt(1, quantidade); /* 'nome2' é o nome que vai entrar no lugar do 'nome1' */
			stmt.setString(2, nome);
			stmt.execute();
			conexao.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void update(Produto produto, String nomeAntigo) {
		
		String sql = " UPDATE produto SET nome = ?, descricao = ?, valor = ?, quantidade = ? WHERE nome = ? ";
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		try {
			conexao = new Conexao().getConnection();
			conexao.setAutoCommit(false);
			stmt = conexao.prepareStatement(sql);
			
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());
			stmt.setBigDecimal(3, produto.getValor());
			stmt.setInt(4, produto.getQuantidade());
			stmt.setString(5, nomeAntigo);
			stmt.execute();
			conexao.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void remove (Integer id) {
		
		String sql = " DELETE FROM produto WHERE id = ? ";
		
		Connection conexao = null;
		PreparedStatement stmt = null;
		try {
			conexao = new Conexao().getConnection();
			conexao.setAutoCommit(false);
			stmt = conexao.prepareStatement(sql);
			
			stmt.setInt(1, id);
			stmt.execute();
			conexao.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
