package br.com.robsonlmds.fipecar.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.robsonlmds.fipecar.model.Dados;
import br.com.robsonlmds.fipecar.model.Veiculo;
import br.com.robsonlmds.fipecar.model.modelos;
import br.com.robsonlmds.fipecar.service.ConsumoApi;
import br.com.robsonlmds.fipecar.service.ConverteDados;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        var menu = """
                \n*** Opções ***
                Carro
                Moto
                Caminhão

                Digite uma das opções para consultar:
                """;

        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;

        if(opcao.toLowerCase().contains("car")){
            endereco = URL_BASE + "carros/marcas";
        }else if(opcao.toLowerCase().contains("mot")){
            endereco = URL_BASE + "motos/marcas";
        }else{
            endereco = URL_BASE + "caminhões/marcas";
        }

        var json = consumo.obterDados(endereco);
        // System.out.println(json);

        var marcas = conversor.obterLista(json, Dados.class);
                        marcas.stream()
                            .sorted(Comparator
                                .comparing(Dados::codigo))
                                    .forEach(System.out::println);

        System.out.println("\nInforme o codigo da marca para consultar: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, modelos.class);

        System.out.println("\n========== Modelos dessa marca ==========");
        modeloLista.modelos()
        .stream()
        .sorted(Comparator
        .comparing(Dados::codigo))
        .forEach(System.out::println);
        
        System.out.println("\nDiga o codigo do carro a ser buscado: ");
        var codigoModelo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos()
                .stream().filter(m -> m.nome().toLowerCase()
                .contains(codigoModelo.toLowerCase())).collect(Collectors.toList());

        System.out.println("\n========== Modelos filtrados ==========");
        modelosFiltrados.forEach(System.out::println);

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.err.println("\nTodos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}
