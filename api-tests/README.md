# QA Dog API - Testes Automatizados

## Visão Geral

Este projeto implementa uma suíte completa de testes automatizados para a **Dog CEO API**, desenvolvido como parte de um desafio técnico para QA Senior. O projeto utiliza **Java**, **JUnit 5** e **RestAssured** para validar os endpoints da API de forma abrangente.

## API Testada

**Base URL:** https://dog.ceo/api

**Endpoints cobertos:**
- `GET /breeds/list/all` - Lista todas as raças disponíveis
- `GET /breed/{breed}/images` - Retorna imagens de uma raça específica
- `GET /breeds/image/random` - Retorna uma imagem aleatória de qualquer raça

## Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|------------|--------|-----------|
| **Java** | 17 | Linguagem principal |
| **JUnit 5** | 5.10.2 | Framework de testes |
| **RestAssured** | 5.4.0 | Testes de API REST |
| **Maven** | 3.x | Gerenciamento de dependências |
| **JSON Schema Validator** | 5.4.0 | Validação de contratos |

## Pré-requisitos

### Software Necessário

1. **Java Development Kit (JDK) 17 ou superior**
   ```bash
   java -version
   # Deve retornar: java version "17.x.x" ou superior
   ```

2. **Apache Maven 3.6 ou superior**
   ```bash
   mvn -version
   # Deve retornar: Apache Maven 3.x.x
   ```

3. **Git** (para clonar o repositório)
   ```bash
   git --version
   ```

### Conectividade

- Conexão com a internet para acessar a Dog CEO API
- Acesso liberado para o domínio `dog.ceo`

## Configuração do Ambiente

### 1. Clone do Repositório

```bash
git clone <https://github.com/TiagoT15/qa-dogapi-restassured.git>
cd qa-dog-api/api-tests
```

### 2. Instalação das Dependências

```bash
mvn clean install
```

### 3. Verificação da Configuração

```bash
mvn test-compile
```

## Estrutura do Projeto

```
api-tests/
├── src/
│   ├── main/java/
│   │   └── com/tiagopereira/qa/
│   │       └── App.java
│   └── test/
│       ├── java/com/tiagopereira/api/
│       │   ├── base/
│       │   │   └── TestBase.java          # Configuração base dos testes
│       │   └── tests/
│       │       ├── BreedsTest.java        # Testes para /breeds/list/all
│       │       ├── BreedImagesTest.java   # Testes para /breed/{breed}/images e /breeds/image/random
│       │       └── BreedNotFoundTest.java # Testes de cenários de erro
│       └── resources/
│           ├── schemas/
│           │   └── breeds-list-all.schema.json  # Schema JSON para validação
│           └── junit-platform.properties        # Configurações do JUnit
├── pom.xml                                      # Dependências Maven
└── README.md                                    # Esta documentação
```

## Execução dos Testes

### Executar Todos os Testes

```bash
mvn test
```

### Executar Testes por Classe

```bash
# Testes de cenários positivos para listagem de raças
mvn test -Dtest=BreedsTest

# Testes de imagens por raça e imagem aleatória
mvn test -Dtest=BreedImagesTest

# Testes de cenários de erro
mvn test -Dtest=BreedNotFoundTest
```

### Executar Teste Específico

```bash
# Exemplo: executar apenas o teste de contrato básico
mvn test -Dtest=BreedsTest#listAllBreeds_contratoBasico
```

### Executar com Logs Detalhados

```bash
mvn test -Dtest.loglevel=DEBUG
```

## Cobertura de Testes

### Cenários Positivos (13 testes)

**GET /breeds/list/all (9 testes):**
- Validação de contrato básico com JSON Schema
- Validação estrutural completa
- Verificação de raças conhecidas
- Validação de raças com e sem sub-raças
- Teste de performance (< 2 segundos)
- Validação de headers HTTP
- Consistência de dados
- Validações específicas baseadas na resposta real

**GET /breed/{breed}/images (1 teste):**
- Teste parametrizado para múltiplas raças (hound, pug, bulldog)

**GET /breeds/image/random (3 testes):**
- Validação básica da URL retornada
- Validação completa com regex pattern
- Teste de performance (< 3 segundos)

### Cenários Negativos (10 testes)

**Erros de Dados:**
- Raça inexistente → 404 Not Found
- Entradas inválidas → 400 Bad Request
- Sub-raça inexistente → 404 Not Found

**Erros de Método HTTP:**
- POST em endpoints GET → 405 Method Not Allowed

**Comportamentos Esperados:**
- Parâmetros desnecessários → 200 (ignorados)
- Endpoints inexistentes → 404 Not Found

## Estratégia de Testes

### Tipos de Validação Implementados

1. **Contract Testing**
   - Validação de JSON Schema
   - Verificação de estrutura de resposta

2. **Functional Testing**
   - Validação de regras de negócio
   - Verificação de dados específicos

3. **Performance Testing**
   - Tempo de resposta aceitável
   - Limites de timeout configurados

4. **Security Testing**
   - Validação de métodos HTTP permitidos
   - Tratamento de entradas maliciosas

5. **Error Handling Testing**
   - Cenários de erro específicos
   - Códigos de status HTTP corretos

### Padrões Utilizados

- **Page Object Model** adaptado para APIs
- **Data-Driven Testing** com testes parametrizados
- **Inheritance** para reutilização de configurações
- **Fluent Interface** do RestAssured

## Relatórios de Teste

### Relatório Surefire (Maven)

Após a execução, os relatórios ficam disponíveis em:
```
target/surefire-reports/
├── TEST-*.xml                    # Relatórios XML
└── *.txt                        # Logs de execução
```

## Relatórios e Logs

### Gerar Relatórios HTML

```bash
# Gerar relatório HTML do Surefire
mvn surefire-report:report

# Gerar site completo com relatórios
mvn clean test site

# Abrir relatório no navegador
# Arquivo: target/site/surefire-report.html
```

### Ver Logs Detalhados

```bash
# Ver logs de falhas específicas por classe
type "target\surefire-reports\*.txt"

# Ver log específico de uma classe
type "target\surefire-reports\com.tiagopereira.api.tests.BreedNotFoundTest.txt"

# Executar com logs detalhados do Maven
mvn test -X

# Logs específicos do RestAssured
mvn test -Drest-assured.log=all

# Executar teste específico com logs
mvn test -Dtest=BreedNotFoundTest -X
```

### Localização dos Relatórios

```
target/
├── surefire-reports/          # Logs detalhados (.txt) e relatórios XML
│   ├── *.txt                  # Logs de execução por classe
│   └── TEST-*.xml             # Relatórios XML estruturados
└── site/
    └── surefire-report.html   # Relatório HTML navegável
```

## Configurações Importantes

### TestBase.java

Configurações centralizadas:
- Base URI da API
- Headers padrão
- Logging automático em falhas
- RequestSpecification reutilizável

### junit-platform.properties

Configurações do JUnit:
- Lifecycle por método
- Execução sequencial
- Display names customizados

## Troubleshooting

### Problemas Comuns

**1. Falha de Conectividade**
```
Erro: Connection refused
Solução: Verificar conexão com internet e acesso ao dog.ceo
```

**2. Timeout nos Testes**
```
Erro: Read timed out
Solução: Verificar estabilidade da rede ou aumentar timeout
```

**3. Falha de Compilação**
```
Erro: Java version mismatch
Solução: Verificar se JDK 17+ está configurado
```

### Logs de Debug

Para investigar falhas, habilite logs detalhados:
```bash
mvn test -X -Dtest=NomeDoTeste
```

## Manutenção

### Atualizando Dependências

```bash
# Verificar versões disponíveis
mvn versions:display-dependency-updates

# Atualizar versões no pom.xml conforme necessário
```

### Adicionando Novos Testes

1. Criar nova classe em `src/test/java/com/tiagopereira/api/tests/`
2. Estender `TestBase`
3. Implementar testes seguindo os padrões existentes
4. Executar `mvn test` para validar

## Issues Conhecidos da API

- **Status Code Incorreto**: A Dog CEO API retorna 404 para parâmetros malformados quando deveria retornar 400 Bad Request (RFC 7231)
- **Testes Afetados**: `breedInvalido_deveRetornar400` - 5 testes falham por validarem corretamente os padrões HTTP
- **Impacto**: Falhas esperadas que demonstram não conformidade da API com padrões REST

## Métricas de Qualidade

- **Total de Testes:** 29
- **Cobertura de Endpoints:** 100% (3/3)
- **Cenários Positivos:** 24 testes
- **Cenários Negativos:** 5 testes (com falhas esperadas)
- **Validações HTTP:** Status + Headers + Body
- **Performance Testing:** Todos os endpoints
- **Contract Testing:** JSON Schema validation
- **Taxa de Sucesso:** 82,8% (24/29 - falhas são bugs da API)

## Contato

**Desenvolvido por:** Tiago Pereira  
**Projeto:** Desafio Técnico QA Senior  
**Tecnologias:** Java 25 + JUnit 5 + RestAssured + Maven