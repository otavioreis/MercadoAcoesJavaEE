# MercadoAcoesJavaEE
Trabalho final da disciplina Arquitetura de Java EE - Mercado de ações em Java EE

## Aluno
**_OTÁVIO AUGUSTO DE QUEIROZ REIS_**<br />

## Documentação do projeto
https://documenter.getpostman.com/view/2111559/Rzn9uMZe

## Emails recebidos após o processamento
![alt text](https://i.snag.gy/KI7Hpl.jpg)
![alt text](https://i.snag.gy/3CgRkN.jpg)


Foi desenvolvido um projeto completo de mercado de ações, utilizando as seguintes tecnologias:

- Sprint Initializr
	- https://start.spring.io/
	- Group: com.javaee.otavio
	- Artifact: mercadodeacoes

- Dependências do projeto
	- RabbitMQ
	- Web
	- Lombok
	- MongoDB
	- JavaMail API

- Docker 
	- Rabbit MQ
		- docker run -d --hostname rabbitmq --name rabbitmq-management -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:management
		- página de acesso ao Rabbit MQ >> http://localhost:15672

	- MongoDB
		- docker run -p 27017:27017 --name mongodb -d mongo


Estrutura de dados:

- Empresa
	- id: string
	- nome: string
	- email: string
	- dataCriacao: LocalDateTime
	- Ref: Acao
	
- Cliente
	- id: string
	- nome: string
	- email: string
	- dataCriacao: LocalDateTime
	- Ref: Set<Acao>

- Acao
	- id: string
	- nome: string
	- valorInicial: float
	- valorAtual: float
	- dataCriacao: LocalDateTime
	- dataUltimaNegociacao: LocalDateTime
	- Ref: Empresa
	- Ref: Cliente

- Negociacao
	- id: string
	- valor: float
	- dataNegociacao: LocalDateTime
	- tipoNegociacao: Enum TipoNegociacao

	- Ref: Acao
	- Ref: Empresa
	- Ref: (Cliente)ClienteVendedor
	- Ref: (Cliente)ClienteComprador


- TipoNegociacao (Enum)
	- CompraCliente
	- VendaCliente
	- VendaEmpresa

