# How to setup the database
## Setup in Glassfish
### Setup Connection Pool
1. Go to http://localhost:4848
2. Open Resources > JDBC > JDBC Connection Pools
3. Click on New
4. Enter following data:
   - Name: Helfer_DB_Pool
   - Resource Type: javax.sql.DataSource
   - Database Driver Vendor: Derby
5. After inserting the data, click on next
6. Scroll down to Additional Properties and insert these properties:
   - DatabaseName --> HelferDB
   - User --> Admin
   - Password --> !sysadmin
7. Click on Finish
8. After creating your new pool verify that the classname of the pool is "org.apache.derby.jdbc.ClientDataSource". It could happen, that there stands "org.apache.derby.jdbc.ClientDataSource40". In this case just remove the "40" from the name. *(Optional)*

### Setup Resource
1. Open Resources > JDBC > JDBC Resources
2. Click New
3. Enter these data:
   - Name: Helfer_DB_Resource
   - Pool Name: Helfer_DB_Pool
4. Save your resource

## Setup in SQL Workbench
1. Open your SQL Workbench
2. Normally the Connection Profile frame should appear, if not, open it
3. Create new profile at top left corner
4. Name the profile "HelferDB"
5. Select Apache Derby Network Client as Driver. Maybe you need to select the path to the file. You can find the file in following folder of your glassfish installation *./glassfish7/javadb/lib/derbyclient.jar*
6. Enter this as URL *jdbc:derby://localhost:1527/HelferDB;create=true*
7. Enter "Admin" as Username and "!sysadmin" as Password
8. Test your connection, if you did everything correct, then your test should be successfull.
9. Press OK to activate the connection

# Start database server
1. Navigate to the bin folder of your glassfish installation, where you can find asadmin
2. Open a cmd in this folder
3. Enter "asadmin start-database"
4. Your database is started and you should be able to start the application