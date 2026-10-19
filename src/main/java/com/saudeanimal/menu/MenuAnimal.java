package com.saudeanimal.menu;

import com.saudeanimal.dao.AnimalDAO;
import com.saudeanimal.dao.SurtoDoencaDAO;
import com.saudeanimal.modelo.Animal;
import com.saudeanimal.modelo.SurtoDoenca;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MenuAnimal extends MenuCrud<Animal> {

    private static final BigDecimal PESO_MAXIMO = new BigDecimal("99999.99");
    private static final BigDecimal ALTURA_MAXIMA = new BigDecimal("9999.99");

    private final AnimalDAO animalDAO;
    private final SurtoDoencaDAO surtoDAO;

    public MenuAnimal(Entrada entrada, AnimalDAO animalDAO, SurtoDoencaDAO surtoDAO) {
        super(entrada, animalDAO);
        this.animalDAO = animalDAO;
        this.surtoDAO = surtoDAO;
    }

    @Override
    protected String titulo() {
        return "ANIMAIS";
    }

    @Override
    protected int id(Animal animal) {
        return animal.getIdAnimal();
    }

    @Override
    protected void exibir(Animal animal) {
        System.out.println("[" + animal.getIdAnimal() + "] " + animal.getNome());
        System.out.println("    Raça: " + Formato.texto(animal.getRaca()) + " | Gênero: " + Formato.texto(animal.getGenero()));
        System.out.println("    Nascimento: " + Formato.data(animal.getNascimento())
                + " | Peso: " + Formato.decimal(animal.getPesoKg(), "kg")
                + " | Altura: " + Formato.decimal(animal.getAlturaCm(), "cm"));
    }

    @Override
    protected Animal novo() {
        String nome = entrada.lerTexto("Nome", 50, true);
        String raca = entrada.lerTexto("Raça", 50, false);
        String genero = entrada.lerTexto("Gênero", 20, false);
        LocalDate nascimento = entrada.lerData("Data de nascimento", false);
        BigDecimal peso = entrada.lerDecimal("Peso em kg", PESO_MAXIMO, false);
        BigDecimal altura = entrada.lerDecimal("Altura em cm", ALTURA_MAXIMA, false);
        return new Animal(nome, raca, genero, nascimento, peso, altura);
    }

    @Override
    protected void editar(Animal animal) {
        animal.setNome(entrada.lerTextoOuManter("Nome", animal.getNome(), 50, true));
        animal.setRaca(entrada.lerTextoOuManter("Raça", animal.getRaca(), 50, false));
        animal.setGenero(entrada.lerTextoOuManter("Gênero", animal.getGenero(), 20, false));
        animal.setNascimento(entrada.lerDataOuManter("Data de nascimento", animal.getNascimento(), false));
        animal.setPesoKg(entrada.lerDecimalOuManter("Peso em kg", animal.getPesoKg(), PESO_MAXIMO, false));
        animal.setAlturaCm(entrada.lerDecimalOuManter("Altura em cm", animal.getAlturaCm(), ALTURA_MAXIMA, false));
    }

    @Override
    protected String avisoExclusao() {
        return "Atenção: o histórico de saúde, as vacinas, as informações genéticas e os vínculos com surtos deste animal também serão excluídos.";
    }

    @Override
    protected List<String> opcoesExtras() {
        return List.of("Buscar por nome", "Listar surtos do animal");
    }

    @Override
    protected void executarExtra(int indice) {
        if (indice == 0) {
            String parte = entrada.lerTexto("Parte do nome", 50, true);
            mostrar(animalDAO.buscarPorNome(parte));
        } else {
            listarSurtos();
        }
    }

    private void listarSurtos() {
        Optional<Animal> animal = escolherExistente();
        if (animal.isEmpty()) {
            return;
        }
        List<SurtoDoenca> surtos = surtoDAO.listarPorAnimal(animal.get().getIdAnimal());
        if (surtos.isEmpty()) {
            System.out.println("Este animal não está vinculado a nenhum surto.");
            return;
        }
        System.out.println("Surtos de " + animal.get().getNome() + ":");
        surtos.forEach(s -> System.out.println("  [" + s.getIdSurto() + "] " + s.getNomeSurto()
                + " - " + Formato.periodo(s.getDataInicio(), s.getDataFim())));
    }
}
