package br.com.estoque.executavel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.estoque.entities.Produto;
import br.com.estoque.model.ProdutoModel;

public class Main {

	public static void adicionar()
			throws SQLException { /*
									 * static - serve para um metodo Estatico conseguir acessar outro metodo (esse
									 * metodo que vai acessar precisa ser 'static')
									 */

		Scanner sc = new Scanner(System.in);

		ProdutoModel pm = new ProdutoModel();

		Produto produto = new Produto();

		System.out.println(" Adicione um Nome: ");
		produto.setNome(sc.next());

		System.out.println(" Adicione uma Descrição: ");
		produto.setDescricao(sc.next());

		System.out.println(" Adicione um Valor: ");
		produto.setValor(sc.nextBigDecimal());

		System.out.println(" Adicione uma Quantidade: ");
		produto.setQuantidade(sc.nextInt());

		pm.register(produto); /*
								 * Fizemos o registro desse produto, chamamos o metodo cadastrar da Model
								 * passando a instancia de 'produto'
								 */

		System.out.println(" ** Produto Registrado com Sucesso!!! ** \n\n");
		System.out.println(" ** Voltando ao Menu Principal **");
		menuPrincipal();

		sc.close();

	}

	public static void consultarProdutos() throws SQLException {

		System.out.println(" ** Lista de produtos do Nosso Estoque **");
		ProdutoModel pm = new ProdutoModel();

		ArrayList<Produto> allProdutos = pm.getProdutos(); /* Vai devolver a arrayList de todos os produtos na tela */

		for (Produto p : allProdutos) {
			System.out.printf("id: " + p.getId() + "\nNome:" + p.getNome() + "\nDescrição: " + p.getDescricao()
					+ "\nValor: " + p.getValor() + "\nQuantidade: " + p.getQuantidade() + "\n\n");
		}
	}

	public static Integer consultarByNome() throws SQLException {

		ProdutoModel pm = new ProdutoModel();

		Scanner sc = new Scanner(System.in);

		System.out.println(" Digite o Nome para Consulta");
		Produto produtoConsultado = pm.getProdutoByNome(sc.next());

		System.out.printf("Id: " + produtoConsultado.getId() + "\nNome: " + produtoConsultado.getNome()
				+ "\nDescricao: " + produtoConsultado.getDescricao() + "\nValor: " + produtoConsultado.getValor()
				+ "\nQauntidade: " + produtoConsultado.getQuantidade() + "\n\n");

		System.out.println(" ** Gostaria de Consultar Outro Produto: \n1 - Sim, \n2 - Não ");
		int respostaConsultar = sc.nextInt();

		return respostaConsultar;

	}

	public static void menuPrincipal() throws SQLException {

		Scanner sc = new Scanner(System.in);

		ProdutoModel pm = new ProdutoModel();

		System.out.println(
				"O que vc Deseja Fazer: \n 1 - Adicionar, \n 2 - Consultar, \n 3 - Atualizar, \n 4 - Remover, \n 5 - Consultar Todos os Produtos");
		int numero = sc.nextInt();

		switch (numero) { /* Ele vai escolher qual é o caso, 'switch' - escolher */
		case 1:
			adicionar();

			break;
		case 2:

			int respostaConsultar = consultarByNome();

			switch (respostaConsultar) {
			case 1:
				consultarByNome();

				break;

			case 2:
				System.out.println(" ** Voltando ao Menu Principal ** ");
				menuPrincipal();

			}

			break;
		case 3:

			System.out.println(" ** Lista de produtos do Nosso Estoque **");

			ArrayList<Produto> listaProdutos = pm.getProdutos();

			System.out.println(" Qual produto voce deseja atualizar");

			System.out.println(
					" O que vc Deseja Atualizar: \n 1 - Nome, \n 2 - Descrição, \n 3 - Valor, \n 4 - Quantidade \n 5 - Atualizar todas as Opções");
			int opcaoUpdate = sc.nextInt();

			switch (opcaoUpdate) {
			case 1:

				System.out.println(" Atualize o Nome do Produto: ");
				String nomeProduto = sc.next();
				pm.updateNomeProduto(nomeProduto);

				break;

			case 2:

				System.out.println(" Atualize a Descrição do Produto: ");
				String descricaoProduto = sc.next();
				pm.updateDescricaoProduto(descricaoProduto);

				break;

			case 3:

				System.out.println(" Atualize o Valor do Produto: ");
				BigDecimal valorProduto = sc.nextBigDecimal();
				pm.updateValorProduto(valorProduto);

				break;

			case 4:

				System.out.println(" Atualize a quantidade do Produto: ");
				Integer quantidadeProduto = sc.nextInt();
				pm.updateQuantidadeProduto(quantidadeProduto);

				break;

			case 5:

				Produto produtoAtualizado = new Produto();

				System.out.println(" Atualize o Nome do Produto: ");
				produtoAtualizado.setDescricao(sc.next());

				System.out.println(" Atualize a Descrição do Produto: ");
				produtoAtualizado.setDescricao(sc.next());

				System.out.println(" Atualize o Valor do Produto: ");
				produtoAtualizado.setValor(sc.nextBigDecimal());

				System.out.println(" Atualize a Quantidade do Produto: ");
				produtoAtualizado.setQuantidade(sc.nextInt());

				pm.update(produtoAtualizado);

			default:

				System.out.println(" ** Nenhuma das Opções Acima ** ");

				break;
			}

		case 4: // Remover

		case 5:
			
			consultarProdutos();
			
			System.out.println(" ** Gostaria de Voltar ao Menu Principal: \n 1 - Sim, \n 2 - Não");
			Scanner selecione = new Scanner(System.in);
			int numeroMenuPrincipal = sc.nextInt();
			switch (numeroMenuPrincipal) {
			case 1:
				menuPrincipal();
				break;

			default:
				System.out.println(" ** Encerrando Programa ** ");
				
				break;
			}
			
		}

		sc.close();
	}

	public static void main(String[] args) throws SQLException {

		menuPrincipal();

	}

}
