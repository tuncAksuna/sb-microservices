## To run Spring Cloud Config Application 
- 1) Run command : docker run -it --rm --name rabbitmqforadvancedmicroservice -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
- 2) Go to https://console.hookdeck.com address run 'hookdeck' listen 8888 source' command from the CMD or Powershell terminal
- 3) Finally, Run the config server application from the IDEA

### Test: 
1) Go to the GitHub configuration server address
   2) URI address at application.yml of config server application
2) Make changes to one of the files with .yml extension and commit the change
3) Finally, look at the application logs and hookdeck logs. You should see 200 replies to Hookdeck logs
