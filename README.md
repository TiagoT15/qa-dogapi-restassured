# restassured-dogapi-tests

##  Descrição do Projeto

Este projeto representa um estudo técnico avançado de automação de testes de API, desenvolvido para demonstrar a aplicação de boas práticas de QA Sênior utilizando Rest-Assured, Java e Maven. O foco principal está na validação funcional, de contrato e consistência de dados através de testes automatizados robustos e escaláveis.

O projeto utiliza a Dog API pública (https://dog.ceo/dog-api/) como sistema sob teste, explorando diferentes cenários de validação incluindo testes positivos, negativos, de contrato JSON e verificações de consistência entre endpoints. A escolha desta API permite demonstrar técnicas avançadas de automação em um ambiente real e estável.

A arquitetura do projeto segue padrões de mercado para automação de APIs, priorizando manutenibilidade, legibilidade e reutilização de código, características essenciais para projetos de automação em ambientes corporativos.

##  Stack Técnica

- **Java 17+** - Linguagem de programação principal
- **Maven** - Gerenciamento de dependências e build
- **Rest-Assured** - Framework para automação de testes de API
- **JUnit 5** - Framework de testes unitários e de integração
- **Hamcrest Matchers** - Biblioteca para assertions expressivas
- **Allure Report** - Geração de relatórios detalhados (integração futura)

##  Estrutura do Projeto

```
src
└── test
    └── java
        └── com.tiagopereira.api
            ├── base
            │   └── TestBase.java
            └── tests
                ├── BreedsTest.java
                ├── BreedImagesTest.java
                ├── BreedNotFoundTest.java
                └── RandomImageTest.java
```

### Descrição das Classes

- **TestBase.java** - Classe base contendo configurações comuns, setup de Rest-Assured e métodos utilitários
- **BreedsTest.java** - Testes de validação da listagem completa de raças e estrutura de dados
- **BreedImagesTest.java** - Testes de busca por imagens de raças específicas e validação de conteúdo
- **RandomImageTest.java** - Testes de consistência para imagens aleatórias e validação cruzada
- **BreedNotFoundTest.java** - Testes negativos para cenários de erro e tratamento de exceções

##  Endpoints Automatizados

| Endpoint | Método | Objetivo |
|----------|--------|----------|
| `/breeds/list/all` | GET | Valida listagem completa de raças e estrutura do contrato JSON |
| `/breed/{breed}/images` | GET | Valida busca por imagens de raça específica e formato das URLs |
| `/breeds/image/random` | GET | Valida geração de imagem aleatória e consistência com raças existentes |
| `/breed/{invalid}/images` | GET | Valida tratamento de erro para raças inexistentes (Status 404) |

##  Estratégia de Testes

### Testes Funcionais
- Validação de status codes (200, 404)
- Verificação de estrutura de resposta JSON
- Validação de conteúdo e tipos de dados

### Testes de Contrato
- Validação de JSON Schema
- Verificação de campos obrigatórios
- Validação de tipos de dados esperados

### Testes de Consistência
- Verificação cruzada entre endpoints
- Validação de integridade de dados
- Conferência de referências entre recursos

### Testes Negativos
- Validação de cenários de erro
- Verificação de tratamento de exceções
- Testes com parâmetros inválidos

### Baseline Dinâmico
- Detecção de mudanças na lista de raças
- Comparação de hash/diff para identificar alterações
- Monitoramento de estabilidade da API

##  Execução dos Testes

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Conexão com internet

### Executar Testes

```bash
# Clonar o repositório
git clone https://github.com/<seu-usuario>/restassured-dogapi-tests.git
cd restassured-dogapi-tests

# Executar todos os testes
mvn clean test

# Executar testes específicos
mvn test -Dtest=BreedsTest

# Executar com relatório detalhado
mvn clean test -Dmaven.test.failure.ignore=true
```

### Gerar Relatórios (Futuro)

```bash
# Gerar relatório Allure
mvn allure:serve
```

##  Critérios de Sucesso

- **Status Codes**: Todos os testes devem retornar códigos de status esperados (200 para sucesso, 404 para recursos não encontrados)
- **Independência**: Os testes devem ser independentes e executáveis em qualquer ordem
- **Reprodutibilidade**: Resultados consistentes em múltiplas execuções
- **Cobertura**: 100% de sucesso para cenários válidos e tratamento adequado de cenários de erro

##  Boas Práticas e Padrões Utilizados

### Request Object Pattern
Aplicação do padrão Page Object adaptado para APIs, encapsulando requisições e validações em métodos reutilizáveis.

### Naming Convention
Nomenclatura clara e descritiva para classes, métodos e variáveis, seguindo convenções Java e boas práticas de teste.

### Validações com Hamcrest
Uso extensivo de Hamcrest Matchers para assertions expressivas e legíveis, facilitando manutenção e compreensão.

### Separação de Responsabilidades
Divisão clara entre configuração base, utilitários e casos de teste específicos, promovendo reutilização e organização.

### Variáveis Dinâmicas
Implementação de parâmetros dinâmicos para rotas e dados de teste, aumentando flexibilidade e cobertura.

##  Possíveis Evoluções Futuras

- **Testes de Performance**: Integração com K6 ou Gatling para validação de carga e stress
- **CI/CD Pipeline**: Implementação de GitHub Actions para execução automática
- **Relatórios Allure**: Configuração completa para relatórios visuais e detalhados
- **Mock Server**: Expansão para cenários POST/PUT utilizando WireMock ou similar
- **Testes de Segurança**: Implementação de validações OWASP para APIs
- **Containerização**: Docker para ambiente de execução padronizado

##  Autor

**Tiago Pereira**  
QA Sênior | Especialista em Automação de Testes  
Especializado em automação, frameworks de teste e arquiteturas de qualidade

---

*Este projeto demonstra aplicação prática de conceitos avançados de QA e serve como referência para implementação de automação de testes de API em ambientes corporativos.*