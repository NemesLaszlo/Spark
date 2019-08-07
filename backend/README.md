# Backend
## Models
### User
+ ID
+ Gender
+ Sexuality
+ Role?
+ Full name
+ Email
+ Password
+ Description
+ Last online
+ Pictures
### Interest
+ ID
+ From
+ To
### Match
+ ID
+ User 1
+ User 2
+ Messages
+ Points
### Message
+ ID
+ Match
+ Sender
+ Time
+ Text
### Picture
+ ID
+ User
+ Picture (link)

## End points
|Model|End point|Type|Description|
|-|-|-|-|
|User|user/me|GET|Data of the current user.|
||user/register|POST|Registration|
||user/login|POST?|Login|
||user/logout|POST?|Logout|
||user/profile|PUT|Update profile|
||user/update|PUT|Update account|
|Interest|interest/add/{id}|POST|Interest from logged in user to id|
|Match|match/me|GET|Gets all matches|
||match/{id}/message|POST|Send message to match with id|