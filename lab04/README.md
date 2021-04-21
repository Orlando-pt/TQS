# Orlando Macedo 94521

# 1
## a)
- Na classe EmployeeRestControllerTemplateIT são usadas as seguintes expressões
	- assertThat(found).extracting(Employee::getName).containsOnly("bob");
		- verificar se quando chama o método getName de Employee, se retorna algo com o nome "bob"
	- assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		- verificar se o código de resposta em "response" é igual ao código 200 http
	- assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
		- retira o corpo de "response", extrai o getName dos 2 employees que tinham sido adicionados e verifica se os nomes correspondem
- Também é usado na classe EmployeeService_UnitTest
	- assertThat(found.getName()).isEqualTo(name);
	- assertThat(fromDb).isNull();
	- assertThat(doesEmployeeExist).isEqualTo(true);
	- assertThat(doesEmployeeExist).isEqualTo(false);
	- assertThat(fromDb.getName()).isEqualTo("john");
	- assertThat(fromDb).isNull();
	- assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
	
## b)
- fazer mock da database é conseguido a partir do uso do Mockito
- e é usado na classe de teste EmployeeService_UnitTest.java
- no setUp a mock é inicializada com os métodos necessários
- depois nos testes unitários só tem que se chamar os testes que foram mocked e testar apropriadamente

## c)
- The Mockito.mock() method allows us to create a mock object of a class or an interface. Then, we can use the mock to stub return values for its methods and verify if they were called.
- We can use the @MockBean to add mock objects to the Spring application context. The mock will replace any existing bean of the same type in the application context. If no bean of the same type is defined, a new one will be added. This annotation is useful in integration tests where a particular bean – for example, an external service – needs to be mocked.

## d)
- contém os detalhes para configurar o armazenamento persistente
- irá ser usado no caso de se querer testar os testes numa bd como o MySQL
- no caso de utilizar esta forma de teste, nada é mocked e tudo executa até ao fim

# 3
- é preciso criar um ficheiro properties também para a pasta de testes de maneira a integrar a bd mysql
- depois de fazer o pull duma imagem docker mysql
- correr o comando
	- docker run --name mysql5tqs -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=tqsdemo -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo -p 33060:3306 -d mysql
- fazer as configurações necessárias no ficheiro properties
- n esquecer de colocar a dependência do mysql no pom
- usar a anotação de @TestPropertiesSource para carregar o ficheiro de propriedades de teste
