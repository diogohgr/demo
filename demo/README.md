# demo_transfer

## Agendamento de transferencia


## Parametros Consulta

- Endpoint: http://localhost:8080/demo/v1/transfer?page=0&size=20&lang=BR
- page = Pagina atual. Default = 0
- size = Tamanho da página a ser buscada no banco. Default = 10
- lang = Idioma (BR ou US). Default = US
```shell
curl --location --request GET 'http://localhost:8080/demo/v1/transfer'
```
## Post (Salvar)

### REQUEST
```shell
curl --location --request POST 'http://localhost:8080/demo/v1/transfer' \
--header 'Content-Type: application/json' \
--data-raw '{
"amount": 500000,
"feeAmount": 5000,
"type": "B",
"createdDate": "2022-01-28",
"transferDate": "2022-02-30",
"accountOrigin": "1234",
"accountDestination": "4321"
}'
```
### RESPONSE
```shell
{
    "id": 1,
    "amount": 500000,
    "feeAmount": 20000.00,
    "createdDate": "2022-01-28",
    "transferDate": "2022-02-28",
    "accountOrigin": "1234",
    "accountDestination": "2345",
    "type": "C"
}
```

## Actuator (monitoramento )
 
- Actuator: http://localhost:8080/demo/actuator
- Info: http://localhost:8080/demo/actuator/info
- Health check: http://localhost:8080/demo/actuator/health
- Metrics: http://localhost:8080/demo/actuator/metrics

Requisitos:
 - Persistência de dados em memória => Banco H2
 - Código em última versão do Java => 17
 - Código com ferramenta de build => Maven
 - Casos de testes => Ok
 - Testes unitários => Ok
 - Sem arquivos de IDE => .gitignore atualizado
 - Código em inglês => Ok
 - Preparo para intercionalização => Demonstrado no Controller. I18NConfig faz a configuração e o método de leitura exibe como usar. Se US exibe linguagem = Estados unidos no retorno, se BR = Brasil. Default Us. Objetivo apenas de apresentação do recurso.

Recomendações:
- Utilizado Spring Boot em sua última versão com arquitetura modular separada em camadas baseada em suas responsabilidades
- Para executar o projeto Executar a classe DemoApplication.
- Regras de negócio descritas foram aplicadas.