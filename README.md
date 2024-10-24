# Backend test

The application is an example of unit, and integration tests.

It should be able to enable customers to sharing messages.

## Requirements

As a user from a social network,
I want to record a message
To share with my friends.

---

### Criteria

- Publication presenting the following data: identifier, user, message content, publication data and a like counter.
- All data must be filled in;
- The identifier must be unique;
- The user field must comply with Software Requirement 001 - Text field formatting, but limiting the maximum number of characters to 25.

## Scenarios

1. Create a new message;
2. Update a message;
3. Delete a message;

###

Application flow:

Controller -> Service -> Repository
            \            /
                -> DB <- 

