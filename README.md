# Projeto Final Bootcamp
Projeto final da wave4 do bootcamp oferecido pelo Meli
## Solução de desafio grupo03
### INTEGRANTES:
* Emanuelle Besckow Figueiredo
* Pedro Sol Barbosa Montes 
* Ana Gabriela Siqueira Franco 
* Douglas Santos Rodrigues 
* Renan Santana Sousa
* Willy de Jesus Passos

--

## DESAFIO: REQUISITOS E APRESENTAÇÃO:
O Seguinte desafio foi proposto para aplicar os conhecimentos até então alcançados no BootcampIt - Meli


[Aqui se encontra o KICKOFF do projeto](./requisitos/EnunciadoBaseWave4.pdf)

[Aqui se encontra o requisito 1 do projeto](./requisitos/Requito1.pdf)

[Aqui se encontra o requisito 2 do projeto](./requisitos/Requito2.pdf)

[Aqui se encontra o requisito 3 do projeto](./requisitos/Requito3.pdf)

[Aqui se encontra o requisito 4 do projeto](./requisitos/Requito4.pdf)

[Aqui se encontra o requisito 5 do projeto](./requisitos/Requito5.pdf)

[Aqui se encontra o requisito 6 do projeto](./requisitos/Requito6.pdf)

<br><br>

## Ferramentas/tecnologias utilizadas

<br>

### Boas práticas:

- GIT
- Clean Code
- Scrum

<br>

### Na implementação:

- Java
- Springboot (estrutura de projeto)
- Maven (automação e gerenciamento de projeto Java)
- versionamento de API
- métodos HTTP
- padrão de projeto MVC
- Testes unitários
- JPA (mapeamento de objeto relacional)
- H2 (SQL)
- JWT (para implementação da autenticação e autorização)

<br>


Observações sobre o projeto/endpoints atendendo os requisitos: 
* Usuário do tipo "B"(comprador) é o único que acessa "/purchaseOrder"
* Usuário do tipo "A"(agente/representante) é o único que acessa "/inboundOrder, /section, /warehouse"
* Para criar uma entrada de estoque (inboundOrder), precisa estar com a section, warehouse e produto(s) já cadastrados.
* Algumas requests de filtragem e ordenação não estão pré-cadastradas no arquivo de insomnia, mas estão funcionais de acordo com os requisitos. Estas dependem de variáveis a seram escolhidas, ou de acordo com cadastros realizados.


<br>
[Coleção de requests Insomnia](./insomnia)

* Usuários já cadastrados:
- {
	"username": "userbuyer",
	"password":"12345"
}  //Usuário comprador
- {
	"username": "useragent",
	"password":"12345"
}  //Usuário agente--o mesmo que representante
