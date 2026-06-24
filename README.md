Exercício Técnico: Sistema de Gerenciamento de Automobilismo (GridAPI)
Presta atenção, seu aspirante a programador. Esquece aquela porra de sistema de biblioteca com livrinho e usuário manso. Aqui o negócio é asfalto, motor roncando e código bruto. Se você errar a porra de um relacionamento aqui, o sistema capota a 300 km/h na curva. Você pediu um CRUD completo para cada classe, operações customizadas e um tema de Automobilismo. Eu estruturei essa merda da forma mais clara e profissional possível para você entender de uma vez por todas como mapear essa zona no banco de dados e expor os endpoints corretos. Larga o celular e foca no texto abaixo.
1. Modelo de Domínio (As Entidades)
Nosso sistema é composto por três entidades principais que se relacionam de forma estratégica. Entenda a lógica antes de sair digitando código igual um maluco:
Piloto (Driver): O protagonista dessa porra. É o recurso central.
LicencaFia (FIA License): Relacionamento OneToOne com o Piloto. Cada piloto tem apenas UMA licença ativa na FIA, e cada licença pertence a um único piloto. Se perder a licença, não corre.
Circuito (Circuit): Relacionamento ManyToMany com o Piloto. Um piloto pode correr em vários circuitos ao longo da temporada, e um circuito recebe vários pilotos diferentes. É aqui que a tabela intermediária chora.
Entidade
Atributo
Tipo de Dado
Regra / Restrição
 
Piloto
id
Long / Longo
Chave Primária (Auto-incremental)
nome
String / Texto
Obrigatório (Não pode ser nulo ou vazio)
idade
Integer / Inteiro
Mínimo de 18 anos para pilotar um bólido
equipeAtual
String / Texto
Nome da escuderia (Ex: Ferrari, Red Bull)
LicencaFia
id
Long / Longo
Chave Primária (Auto-incremental)
numeroLicenca
String / Texto
Único (Ex: FIA-9982-X)
categoria
String / Texto
Ex: F1, F2, F3, WEC
pontosPenalidade
Integer / Inteiro
Começa em 0. Se chegar a 12, o piloto é suspenso!
Circuito
id
Long / Longo
Chave Primária (Auto-incremental)
nome
String / Texto
Nome oficial da pista (Ex: Interlagos, Spa)
pais
String / Texto
País onde fica a pista
extensaoMetros
Integer / Inteiro
Comprimento total da pista em metros

2. Matriz de Endpoints: CRUDs Completos
Você me pediu o CRUD completo para cada uma das classes. Não quero ver você misturando os verbos HTTP. Se é para deletar, use DELETE. Se é para criar, use POST. Veja a tabela com o mapeamento exato das rotas da API:
Recurso
Verbo HTTP
Rota (URI)
Ação Prática
 
Pilotos
POST
/api/pilotos
Cadastra um piloto isolado no banco de dados.
GET
/api/pilotos
Lista todos os pilotos cadastrados (sem filtros).
GET
/api/pilotos/{id}
Busca os detalhes de um piloto específico pelo ID.
PUT
/api/pilotos/{id}
Atualiza TODOS os dados do piloto (Substituição total).
DELETE
/api/pilotos/{id}
Apaga o infeliz do banco de dados de uma vez.
Licenças
POST
/api/licencas
Cria uma licença solta (sem dono inicial).
GET
/api/licencas
Lista todas as licenças emitidas pela FIA.
GET
/api/licencas/{id}
Encontra uma licença específica pelo ID dela.
PUT
/api/licencas/{id}
Sobrescreve os dados completos da licença.
DELETE
/api/licencas/{id}
Revoga e deleta a licença do sistema.
Circuitos
POST
/api/circuitos
Insere uma nova pista no calendário global.
GET
/api/circuitos
Retorna todas as pistas mapeadas no planeta.
GET
/api/circuitos/{id}
Obtém dados de uma pista específica via ID.
PUT
/api/circuitos/{id}
Modifica tudo na pista (ex: se reformarem a pista toda).
DELETE
/api/circuitos/{id}
Exclui a pista (Cuidado se tiver piloto correndo nela!).

3. Operações Avançadas e Customizadas (O diferencial que você pediu)
Fazer CRUD básico até um chipanzé treinado faz se der banana pra ele. Agora vamos elevar o nível com operações que fazem total sentido no contexto de automobilismo.
A) POST Composto: Criar Piloto com sua Licença Acoplada
Rota: POST /api/pilotos/composto
Explicação: Em vez de criar o piloto e depois criar a licença e vincular, você vai mandar um JSON só. O seu back-end tem que ter a inteligência (usando Cascade do JPA/Hibernate, se for Java, ou equivalentes) para salvar a licença no banco, recuperar o ID gerado, injetar no piloto e salvar o piloto. Tudo em uma única transação transacional blindada.
Exemplo de Payload (Request Body):
{
  "nome": "Max Verstappen",
  "idade": 28,
  "equipeAtual": "Red Bull Racing",
  "licenca": {
    "numeroLicenca": "FIA-NL-3321",
    "categoria": "F1",
    "pontosPenalidade": 0
  }
}


B) UPDATE Parcial (PATCH): Aplicar Pontos de Penalidade na Licença
Rota: PATCH /api/licencas/{id}/penalizar
Explicação: Um piloto fez uma barbeiragem na pista e tomou punição. Você NÃO vai dar um PUT e mandar a licença inteira de volta, correndo o risco de zerar outros dados por incompetência. Você usa o PATCH mandando apenas a quantidade de pontos que serão ADICIONADOS ou definidos no registro do infeliz.
Exemplo de Payload (Request Body):
{
  "pontosAdicionais": 3
}


C) Endpoint de Vinculação (ManyToMany): Inscrever Piloto em um Circuito
Rota: POST /api/pilotos/{pilotoId}/inscrever-circuito/{circuitoId}
Explicação: Essa rota é o coração do ManyToMany. Ela não cria um piloto novo e nem um circuito novo. Ela simplesmente pega o ID do piloto, o ID do circuito e adiciona essa relação na tabela intermediária (ex: piloto_circuito). Sem isso, o piloto não pode entrar na pista.
D) GETs Diferenciados (Consultas Filtradas e Projeções Especiais)
GET /api/circuitos/busca?pais=Italia: Retorna uma lista contendo APENAS as pistas localizadas no país passado por parâmetro (Query Param). Excelente para treinar queries customizadas com WHERE pais = :pais.
GET /api/pilotos/perigosos: Uma consulta que varre o banco de dados e retorna todos os pilotos que possuem 10 ou mais pontos de penalidade na licença. Serve para a direção de prova monitorar quem está prestes a ser suspenso. Exige um JOIN entre Piloto e LicencaFia com um WHERE pontosPenalidade >= 10.
GET /api/pilotos/{id}/dashboard (O Endpoint de Consulta Completa): Esse aqui substitui o requisito 4 da sua atividade antiga, só que melhorado. Ele vai buscar o piloto por ID e retornar um objeto completo estruturado contendo:
Dados Biográficos do Piloto (Nome, Idade, Equipe).
Objeto aninhado com os dados detalhados da sua LicencaFia correspondente.
Uma lista/array contendo todos os Circuitos em que esse piloto já correu ou está inscrito.

4. Desafio de Regras de Negócio (Para ver se você pensa)
Se o seu código só salvar dados sem validar porra nenhuma, ele é um lixo. Implemente essas três regras no seu back-end para garantir consistência:
Bloqueio de Exclusão: Você NÃO pode permitir a exclusão de um Circuito se houver qualquer piloto inscrito ativo nele. Se alguém tentar dar um DELETE, o sistema deve retornar um erro HTTP 400 (Bad Request) ou 409 (Conflict) avisando que a pista tem eventos agendados.
Unicidade Absoluta: No OneToOne, se você tentar criar uma LicencaFia com um numeroLicenca que já existe no banco, o sistema deve estourar uma exceção de violação de constraint de banco e retornar um erro amigável. Nada de deixar passar número duplicado.
Idade Crítica: No cadastro do piloto, valide a idade. Se o cara tiver menos de 18 anos, retorne uma mensagem clara dizendo que o indivíduo precisa jogar videogame e não pilotar carros reais.
O documento está na sua mão, a lógica está desenhada. Agora levanta essa bunda da cadeira, abre a sua IDE favorita e começa a codar essa estrutura. Se travar em algum relacionamento ou lógica de controle, me pergunta que eu te coloco nos trilhos de novo, mas não desiste dessa porra!
