# ParóquiaGestão

API RESTful para controle financeiro de eventos beneficentes de pastorais
(Catequese, ECC, Pastoral da Juventude etc.). Registra grupos, produtos,
vendas e despesas, e gera a prestação de contas por dia, mês e ano.

Projeto da ACE 08 — UniCatólica (Análise e Projeto de Sistemas II).

## Equipe
[NOME COMPLETO E MATRÍCULA DOS INTEGRANTES]

## Problema e impacto social
Vendas e gastos dos eventos eram anotados em papel ou planilhas soltas, o que
dificultava a prestação de contas. A API centraliza os registros e calcula o
balanço (total bruto, despesas e lucro líquido) de cada grupo.

## Tecnologias
Java 17, Spring Boot 3, Spring Data JPA, H2, Bean Validation, springdoc
(Swagger), JUnit 5 e Mockito.

## Como executar
1. Clone o repositório
2. `mvn spring-boot:run`
3. Swagger: http://localhost:8080/swagger-ui.html
4. Console H2: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:paroquiadb`)

## Testes
`mvn test`

## Endpoints
| Método | Caminho | Descrição |
|---|---|---|
| POST | /grupos | Cadastra grupo |
| GET | /grupos | Lista grupos |
| POST | /produtos | Cadastra produto |
| GET | /produtos | Lista produtos |
| POST | /vendas | Registra venda |
| POST | /despesas | Registra despesa |
| GET | /prestacao-contas/{grupoId} | Balanço acumulado do grupo |
| GET | /prestacao-contas/{grupoId}/dia?data=AAAA-MM-DD | Balanço de um dia |
| GET | /prestacao-contas/{grupoId}/mensal?ano=&mes= | Balanço de um mês |
| GET | /prestacao-contas/{grupoId}/anual?ano= | Balanço do ano, mês a mês |

## Arquitetura
Arquitetura em camadas (controller → service → repository → banco).
Detalhes de SOLID e padrões em `docs/arquitetura-e-padroes.md`.

## Diagramas UML
Casos de uso, classes e sequência na pasta `docs/uml/`.

## Limitações conhecidas
- Banco H2 em memória (os dados somem ao reiniciar)
- Valores monetários em `Double` (o ideal em produção é `BigDecimal`)
- Sem autenticação (fora do escopo do MVP)