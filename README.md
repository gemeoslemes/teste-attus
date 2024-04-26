<html>
<body>

<h1>API REST Java Spring Boot Teste Attus</h1>

<h2>Descrição</h2>

A API permite realizar as seguintes operações:
<ul>
  <li>Pessoas: Criar, editar e consultar uma ou mais pessoas.</li>
  <li>Endereços: Criar, editar e consultar um ou mais endereços de uma pessoa.</li>
  <li>Endereço Principal: A API permite indicar qual endereço será considerado o principal de uma pessoa.</li>
</ul>
<p> 
  O propósito da API é fornecer um conjunto de endpoints para gerenciar informações de pessoas e seus endereços de forma simples e eficiente.
  Com essa API, os usuários podem realizar operações (criar, ler e atualizar) em pessoas e seus endereços, 
  além de poderem definir um endereço como principal para cada pessoa.
</p>
  
<h2>Tecnologias Utilizadas</h2>

<ul>
    <li>Java</li>
    <li>Spring Boot</li>
    <li>Spring Data JPA</li>
    <li>Lombook</li>
    <li>DevTools</li>
    <li>Hibernate</li>
    <li>MySQL (ou outro banco de dados utilizado)</li>
    <li>Maven</li>
</ul>

<h2>Requisitos</h2>

<ul>
    <li>Java 17 ou superior instalado</li>
    <li>MySQL (ou outro banco de dados compatível) configurado e em execução</li>
</ul>

<h2>Instalação e Configuração</h2>

<ul>
    <li>Clone este repositório para o seu ambiente local.</li>
    <li>Configure o banco de dados MySQL com as configurações adequadas no arquivo <code>application.properties</code>.</li>
    <li>Execute o projeto utilizando o Maven: <code>mvn spring-boot:run</code>.</li>
</ul>
```
<h2>Endpoints de Pessoa</h2>
<ul>
   <li>
    <code>GET localhost:8080/pessoas</code>: Este endpoint retorna uma lista de todas as pessoas cadastradas no sistema.
    <p><strong>Exemplo de Requisição:</strong></p>
    <pre><code>GET /pessoas HTTP/1.1
Host: localhost:8080
Accept: application/json</code></pre>
    <p><strong>Exemplo de Resposta (200 OK):</strong></p>
    <pre><code>HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "nomeCompleto": "Paulo Andrade",
        "dataNascimento": "2000-02-03"
    }  
]</code></pre>
</li>

<li>
    <code>POST localhost:8080/pessoas</code>: Este endpoint é usado para criar uma nova pessoa no sistema. É necessário enviar o nome e a data de nascimento da pessoa no corpo da requisição.
    <p><strong>Exemplo de Requisição:</strong></p>
    <pre><code>POST /pessoas HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "nomeCompleto": "João da Silva",
    "dataNascimento": "1990-05-20"
}</code></pre>
    <p><strong>Exemplo de Resposta (201 Created):</strong></p>
    <pre><code>HTTP/1.1 201 Created
Location: /pessoas/123
Content-Type: application/json

{
    "id": 123,
    "nomeCompleto": "João da Silva",
    "dataNascimento": "1990-05-20"
}</code></pre>
</li>

<li>
    <code>PUT localhost:8080/pessoas/1</code>: Este endpoint é usado para atualizar os dados de uma pessoa existente no sistema. Pode enviar o nome ou a data de nascimento atualizados no corpo da requisição.
    Caso nenhum seja passado ele mantém o valor dos dados.
    <p><strong>Exemplo de Requisição:</strong></p>
    <pre><code>PUT /pessoas/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
    "nomeCompleto": "Novo Nome",
    "dataNascimento": "1998-10-15"
}</code></pre>
    <p><strong>Exemplo de Resposta (200 OK):</strong></p>
    <pre><code>HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "nomeCompleto": "Novo Nome",
    "dataNascimento": "1998-10-15"
}</code></pre>
</li>

<li>
    <code>GET localhost:8080/pessoas/1</code>: Este endpoint é usado para obter os detalhes de uma pessoa pelo seu ID.
    <p><strong>Exemplo de Requisição:</strong></p>
    <pre><code>GET /pessoas/1 HTTP/1.1
Host: localhost:8080</code></pre>
    <p><strong>Exemplo de Resposta (200 OK):</strong></p>
    <pre><code>HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "nomeCompleto": "Paulo Andrade",
    "dataNascimento": "2000-02-03"
}</code></pre>
</li>
   
</ul>

<h2>Endpoints de Enderecos</h2>
<ul>
<li>
    <code>POST localhost:8080/enderecos</code>: Este endpoint é usado para criar um novo endereço no sistema. Principal seria 
  o endereço principal da pessoa, caso ela já tenha, mesmo colocando true ele fica como false, ao menos que atualize o endereço principal para false. 
    <p><strong>Corpo da Requisição:</strong></p>
    <pre><code>{
    "logradouro": "Rua 2",
    "cep": "22222222",
    "numero": 120,
    "cidade": "Estiva", 
    "estado": "MG",
    "pessoa": 1,
    "principal": true
}</code></pre>
    <p><strong>Exemplo de Resposta (201 Created):</strong></p>
    <pre><code>HTTP/1.1 201 Created
Content-Type: application/json

{
    "id": 123,
    "logradouro": "Rua 2",
    "cep": "22222222",
    "numero": 120,
    "cidade": "Estiva", 
    "estado": "MG",
    "pessoa": 1,
    "principal": false
}</code></pre>
</li>

<li>
    <code>PUT localhost:8080/enderecos/1</code>: Este endpoint é usado para atualizar os dados de um endereço existente no sistema. É necessário passar o ID do endereço a ser atualizado e enviar os dados atualizados no corpo da requisição.
    <p><strong>Corpo da Requisição:</strong></p>
    <pre><code>{
    "logradouro": "Rua 2",
    "cep": "05108070",
    "numero": 120,
    "cidade": "São Paulo", 
    "estado": "SP",
    "favorito": false
}</code></pre>
    <p><strong>Exemplo de Resposta (200 OK):</strong></p>
    <pre><code>HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "logradouro": "Rua 2",
    "cep": "05108070",
    "numero": 120,
    "cidade": "São Paulo", 
    "estado": "SP",
    "favorito": false
}</code></pre>
</li>

<li>
    <code>GET localhost:8080/enderecos/1</code>: Este endpoint é usado para obter todos os endereços associados a uma pessoa pelo seu ID.
    <p><strong>Exemplo de Resposta (200 OK):</strong></p>
    <pre><code>HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "logradouro": "Rua 2",
        "cep": 22222222,
        "numero": 120,
        "cidade": "São Paulo",
        "estado": "SP",
        "pessoa": 1,
        "principal": false
    },
    {
        "id": 2,
        "logradouro": "Avenida Rio Branco",
        "cep": 20090900,
        "numero": 150,
        "cidade": "Rio de Janeiro",
        "estado": "RJ",
        "pessoa": 1,
        "principal": false
    }
]</code></pre>
</li>

<li>
    <code>GET localhost:8080/enderecos/1/1</code>: Este endpoint é usado para obter um endereço específico de uma pessoa pelo ID da pessoa e o ID do endereço.
    <p><strong>Exemplo de Resposta (200 OK):</strong></p>
    <pre><code>HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "logradouro": "Rua 2",
    "cep": 22222222,
    "numero": 120,
    "cidade": "São Paulo",
    "estado": "SP",
    "pessoa": 1,
    "principal": false
}</code></pre>
</li>  
</ul>


<h2>Testes</h2>
<p>Para executar os testes unitários, execute o seguinte comando:</p>
<pre><code>mvn test
</code></pre>
<h2>Contribuição</h2>
<p>Se você encontrar algum problema ou tiver alguma sugestão de melhoria, sinta-se à vontade para abrir uma issue ou enviar um pull request.</p>
<h2>Contato</h2>
<p>Para entrar em contato, envie um e-mail para [victorlemes0776@gmail.com].</p>
</body>
</html>
