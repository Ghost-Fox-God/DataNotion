# Data Notion [YouTube Link](https://youtu.be/HhP8h24ofBo)

- ## Annotation Tool that annotates data effortlessly.

# Installation Instructions

## Prerequisites

1. Java Development Kit (JDK) installed on your machine. You can download it from the official Oracle website: [https://www.oracle.com/java/technologies/javase-jdk14-downloads.html](https://www.oracle.com/java/technologies/javase-jdk14-downloads.html)
2. Maven installed on your machine. You can download it from the official Apache Maven website: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
3. Node.js and npm (Node Package Manager) installed on your machine. You can download them from the official Node.js website: [https://nodejs.org/en/download/](https://nodejs.org/en/download/)

## Backend Installation

1. Navigate to the `backend` folder of the project.
2. Open a terminal or command prompt in the `backend` folder.
3. Run the following command to build and install the backend dependencies using Maven:

```
mvn clean install
```

4. After successful installation, you can run the backend server using the following command:

```
mvn spring-boot:run
```

5. The backend server should now be running on http://localhost:8080 by default.

## Frontend Installation

1. Navigate to the frontend folder of the project.
2. Open a terminal or command prompt in the frontend folder.
3. Run the following command to install the frontend dependencies using npm:

```
npm install
```

4.After successful installation, you can run the frontend development server using the following command:

```
npm start
```

5. The frontend development server should now be running on http://localhost:3000 by default.

# Usage Instructions

Data Notion is a role-based project with two user roles:

1. **Manager:** Managers can create projects, assign annotators to the project, assign tasks to annotators, create tags for annotation, and view project statistics.

2. **Annotator:** Annotators can view tasks assigned to them, perform data annotation based on assigned tasks and tags.

# Configuration

Data Notion requires the following configuration:

```
DB_HOST: Database host
DB_PORT: Database port
DB_DATABASE: Database name
DB_USERNAME: Database username
DB_PASSWORD: Database password
```

# Contributing

Contributions to Data Notion are welcome! If you would like to contribute, please follow the standard GitHub workflow of Fork, Branch, and Pull Request.

# DataNotion In Action

## User can SignUp as a Annotator or Manager.

![SignUp](https://user-images.githubusercontent.com/37774202/234565615-964e8e98-0d75-468e-b988-5bd993f5ed12.png)

## Manager can Create projects and can add annotator to the projects.

![projects](https://user-images.githubusercontent.com/37774202/234565827-8a1b6bf2-5c6a-4687-9a1f-14925df6d543.png)

## Manager can Create new Classification/Entity tags to annotate the large dataset. 

![ClassificationandEntityTags](https://user-images.githubusercontent.com/37774202/234566356-268cd075-4b6c-4a11-8079-274b09791f8c.png)

## Annotator and Manager can annotate the dataset by allocating tags to dataset while getting runtime output of annotation.

![Annotation Preview](https://user-images.githubusercontent.com/37774202/234566904-b1b7750d-953d-4cd2-8b36-19db80899028.png)

## Manager can get all the statistics of project with Task Completion, Tags Distribution and many more. 

![Statistics](https://user-images.githubusercontent.com/37774202/234569431-d621f617-6fdd-4b1f-a86f-3a3d4965f381.png)

## Manager Can export Annotated Dataset in JSON format. Export can take some time based on the size of the Dataset. Therefore, DataNotion has background thread pools to process considerable file quantities. This will update the status as pending which will change when processing is completed.

![Export_Data](https://user-images.githubusercontent.com/37774202/234570385-ed5576a4-5c83-4236-a211-cb7ec3e02d23.png)

## Final output of a annotated project looks like:

![Output_OF_ExportedData](https://user-images.githubusercontent.com/37774202/234570540-9986fbac-2858-4923-bdb5-384d3bcd18d3.png)

