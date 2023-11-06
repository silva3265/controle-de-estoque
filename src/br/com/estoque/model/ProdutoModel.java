package br.com.estoque.model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.estoque.dao.ProdutoDao;
import br.com.estoque.entities.Produto;

public class ProdutoModel { /* O Objetivo da 'Model' é fazer a ponte entre a DAO e Programa Principal 'Main' 'View', ea é responsavel por Carregar os Metodos e a Regra de Negocio */

	ProdutoDao pDao = new ProdutoDao();

	/*
	 * O Tipo de retorno dever sempre o que esta depois do 'get', que no caso é do
	 * tipo produto
	 */
	/* e o que vem depois do 'by' é o que vamos passar no Parametro */

	/* Consultar pelo o Nome do Produto */
	public Produto getProdutoByNome(String nome) throws SQLException {
		return pDao.getProdutoByNome(nome);

	}
	
	public Produto getProdutoById(Integer id) throws SQLException {
		return pDao.getProdutoById(id);

	}
	
	public ArrayList<Produto> getProdutos() throws SQLException {
		return pDao.getProdutos();

	}

	/* Cadastrar um Produto */
	public void register(Produto produto) throws SQLException {
		pDao.register(produto); /* Chamamos o DAO */

	}

	public void updateNomeProduto(String nome1, String nome2) throws SQLException {
		pDao.updateNome(nome1, nome2);
		
	}

	public void updateDescricaoProduto(String descricaoProduto, String nome) {
		pDao.updateDescricao(descricaoProduto, nome);
		
	}

	public void updateValorProduto(BigDecimal quantidade, String nome) {
		pDao.updateValor(quantidade, nome);
		
	}

	public void updateQuantidadeProduto(Integer quantidadeProduto, String nome) {
		pDao.updateQuantidade(quantidadeProduto, nome);
		
	}

	public void update(Produto produto, String nomeAntigo) {
		pDao.update(produto, nomeAntigo);
		
	}
	
	public void remove(Integer id) {
		pDao.remove(id);
	}

}
