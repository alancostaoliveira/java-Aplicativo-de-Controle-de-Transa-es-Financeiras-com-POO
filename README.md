# 💰 DIO Bank — Aplicativo de Controle de Transações Financeiras com POO

Projeto desenvolvido como parte do desafio da [DIO (Digital Innovation One)](https://www.dio.me/), consolidando conceitos fundamentais de **Programação Orientada a Objetos (POO)** em Java.

## 📋 Descrição

Sistema bancário básico implementado em Java 17 via console interativo. Permite criar clientes e contas, realizar depósitos, saques, transferências via PIX, criar e resgatar investimentos, e visualizar o histórico de transações.

## 🎯 Objetivos de Aprendizagem

- ✅ Aplicar conceitos de Orientação a Objetos (herança, encapsulamento, polimorfismo, abstração)
- ✅ Implementar estrutura de entidades com herança e composição
- ✅ Criar repositórios simulando persistência em memória
- ✅ Praticar boas práticas com Lombok, enums e records
- ✅ Construir menus e fluxos interativos via console
- ✅ Documentar processos técnicos de forma clara e estruturada

## 🏗️ Estrutura do Projeto

```
src/main/java/com/bank/
├── BankApplication.java              # Ponto de entrada — menu interativo
├── enums/
│   ├── AccountType.java              # CORRENTE, POUPANCA
│   ├── InvestmentType.java           # CDB, LCI, TESOURO_DIRETO
│   ├── PixKeyType.java               # CPF, EMAIL, TELEFONE, CHAVE_ALEATORIA
│   └── TransactionType.java         # DEPOSITO, SAQUE, TRANSFERENCIA_PIX, …
├── model/
│   ├── Account.java                  # Classe abstrata base
│   ├── CheckingAccount.java          # Conta Corrente (com limite)
│   ├── Customer.java                 # Entidade cliente (Lombok)
│   ├── Investment.java               # Investimento com juros compostos
│   ├── PixKey.java                   # Chave PIX associada a uma conta
│   ├── SavingsAccount.java           # Conta Poupança (limite de saques)
│   └── Transaction.java             # Record imutável de transação
├── repository/
│   ├── AccountRepository.java        # Persistência em memória de contas/PIX
│   ├── CustomerRepository.java       # Persistência em memória de clientes
│   └── InvestmentRepository.java    # Persistência em memória de investimentos
└── service/
    └── BankService.java              # Camada de negócio principal
```

## 🧩 Conceitos de POO Aplicados

| Conceito | Onde é aplicado |
|---|---|
| **Abstração** | Classe abstrata `Account` com método abstrato `withdraw()` |
| **Herança** | `CheckingAccount` e `SavingsAccount` herdam de `Account` |
| **Polimorfismo** | `withdraw()` com comportamento diferente em cada subclasse |
| **Encapsulamento** | Lombok `@Getter`/`@Setter`/`@Data`/`@Builder` em todas as entidades |
| **Composição** | `Account` compõe `Customer` e lista de `Transaction` |
| **Records** | `Transaction` como record imutável (Java 16+) |
| **Enums** | `TransactionType`, `AccountType`, `InvestmentType`, `PixKeyType` |

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Build e execução
```bash
# Compilar e gerar o fat JAR
mvn package -q

# Executar a aplicação
java -jar target/financial-control-1.0.0.jar
```

### Execução direta (sem fat JAR)
```bash
mvn compile exec:java -Dexec.mainClass=com.bank.BankApplication
```

## 🖥️ Funcionalidades do Menu

```
=============================================================
  Bem-vindo ao Sistema de Controle Financeiro - DIO Bank
=============================================================

--- MENU PRINCIPAL ---
1. Gerenciar Clientes       → Cadastrar e listar clientes
2. Gerenciar Contas         → Abrir conta corrente ou poupança
3. Transações               → Depósito, saque e extrato
4. PIX                      → Cadastrar chave e transferir
5. Investimentos            → CDB, LCI, Tesouro Direto
0. Sair
```

### Regras de Negócio
- **Conta Corrente**: limite de cheque especial de R$ 500,00
- **Conta Poupança**: máximo de 5 saques por mês
- **PIX**: chave deve estar previamente cadastrada
- **Investimentos**: valor debitado da conta no momento da aplicação; rendimento calculado com juros compostos diários
  - CDB: 12,5% a.a.
  - LCI: 10,0% a.a.
  - Tesouro Direto Selic: 11,75% a.a.

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17 | Linguagem principal |
| Maven | 3.6+ | Build e gerenciamento de dependências |
| Lombok | 1.18.30 | Redução de código boilerplate |

## 📂 Dependências (pom.xml)

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

## 👤 Autor

Desenvolvido como entrega do desafio **"Aplicativo de Controle de Transações Financeiras com POO"** da [DIO](https://www.dio.me/).
