# Backend test

The application should be able to enable customers to sharing messages.

## Requirements

As a user from a social network,
I want to record a message
To share with my friends.

---

### Criteria

- Publicação apresentar os dados: identificador, usuário, conteúdo da mensagem, data da publicação e um contador de gostei.
- Todos os dados devem estar preenchidos;
- Identificador deve ser único;
- Campo usuário deve respeitar o Requisito de Software 001 - Formatação de campo de texto, porém limitando a quantidade máxima de caracteres em 25.

## Scenarios

1. Create a new message;
2. Update a message;
3. Delete a message;

###

Application flow:

Controller -> Service -> Repository
            \            /
                -> DB <- 

