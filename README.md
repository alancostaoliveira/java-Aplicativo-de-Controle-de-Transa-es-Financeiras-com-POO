# 🚀 Desafio DIO: Sistema Bancário com Java e POO

## 📌 Sobre o Projeto
Este projeto foi desenvolvido como parte do desafio da **Digital Innovation One (DIO)** para consolidar os conceitos de **Programação Orientada a Objetos (POO)**. O sistema simula um banco básico, permitindo a criação de contas, transações (depósito, saque, PIX), investimentos e consulta de extrato, tudo via console interativo.

## 🎯 Objetivos de Aprendizagem
- Aplicar **abstração**, **encapsulamento**, **herança** e **polimorfismo**.
- Utilizar **records**, **enums** e **Lombok** para código mais limpo.
- Simular persistência de dados com **repositórios em memória**.
- Criar um menu interativo para interação com o usuário.
- Documentar e versionar o projeto com **Git e GitHub**.

## 🛠️ Tecnologias Utilizadas
- **Java 17** (ou superior)
- **Lombok** (para redução de boilerplate)
- **Git/GitHub** (versionamento e documentação)

## 📁 Estrutura do Projeto
```text
src/
└── br/com/dio/desafio/
    ├── Main.java
    ├── model/
    │   ├── IConta.java (interface)
    │   ├── Cliente.java
    │   ├── Conta.java (abstract)
    │   ├── ContaCorrente.java
    │   ├── ContaPoupanca.java
    │   └── Transacao.java (record)
    ├── enums/
    │   ├── TipoConta.java
    │   ├── TipoTransacao.java
    │   └── TipoInvestimento.java
    ├── repository/
    │   └── ContaRepository.java
    ├── exception/
    │   ├── SaldoInsuficienteException.java
    │   └── ContaNaoEncontradaException.java
    └── service/
        └── CalculadoraInvestimento.java
```

## ⚙️ Como Executar
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/java-Aplicativo-de-Controle-de-Transa-es-Financeiras-com-POO.git
   ```
2. Abra o projeto em sua IDE favorita (IntelliJ, Eclipse, VS Code).
3. Certifique-se de ter o Lombok configurado.
4. Execute a classe `Main.java`.
5. Interaja com o menu através do console.

## 🧪 Exemplos de Uso
### Criar Conta
```text
--- Sistema Bancário DIO ---
1. Criar Conta
2. Depositar
...
Opção: 1
Nome do cliente: João Silva
CPF: 123.456.789-00
Tipo de conta (1-Corrente, 2-Poupança): 1
Conta criada com sucesso! Número: 1
```

### Realizar Transferência PIX
```text
Opção: 4
Número da sua conta: 1
Número da conta destino: 2
Valor: 150.00
Transferência realizada!
```

## 📚 Conceitos Aplicados
- **Herança**: `ContaCorrente` e `ContaPoupanca` herdam de `Conta`.
- **Encapsulamento**: Atributos protegidos e privados com métodos de acesso controlados.
- **Polimorfismo**: O método `aplicarInvestimento` é implementado de forma específica em cada tipo de conta.
- **Abstração**: Uso de Interface (`IConta`) e Classe Abstrata (`Conta`) para definir o contrato do sistema.
- **Records**: Utilização de `Transacao` como um record para representar dados imutáveis.
- **Enums**: Padronização de tipos com `TipoConta`, `TipoTransacao` e `TipoInvestimento`.
- **Repository Pattern**: Centralização da lógica de armazenamento de contas em memória.

## 🚀 Melhorias Implementadas (Diferencial)
Além dos requisitos básicos, foram aplicadas as seguintes melhorias técnicas:
- **Interface Segregation**: Criação da interface `IConta` para definir o contrato de comportamento.
- **Exceções Personalizadas**: Implementação de `SaldoInsuficienteException` e `ContaNaoEncontradaException` para um tratamento de erros mais robusto e profissional.
- **Busca por CPF**: Novo recurso no repositório permitindo localizar contas vinculadas a um CPF específico.
- **Java Streams**: Uso de Streams no repositório para filtragem eficiente de dados.
- **Tratamento de Erros**: Bloco `try-catch` no menu principal para capturar erros de negócio separadamente de erros inesperados.

## 👨+💻 Autor
**Cristiano**
[GitHub](https://github.com/cristiano)

---
✨ Desafio concluído como parte da formação em Java da DIO.