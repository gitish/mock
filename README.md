Provides power to developer and tester to setup and test their application in isolation mode.
## Containerize Mock Service
### Run Steps
```sh
#how to create Image
cd mock/
mvn clean install
docker build -t k8s.gcr.io/sh-mock .
```

#list docker images and run
docker images
docker run -p 8080:8083 -t shl/mock
```

Docker has a simple Dockerfile file format that it uses to specify the "layers" of an image. So let’s go ahead and create a Dockerfile in our Spring Boot project:

## Dockerfile

```docker
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/mock.0.1.0-SNAPSHOTS.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

This Dockerfile is very simple, but that’s all you need to run a Spring Boot app with no frills: just Java and a JAR file. The project JAR file is ADDed to the container as "app.jar" and then executed in the ENTRYPOINT.

We added a VOLUME pointing to "/tmp" because that is where a Spring Boot application creates working directories for Tomcat by default. The effect is to create a temporary file on your host under "/var/lib/docker" and link it to the container under "/tmp". This step is optional for the simple app that we wrote here, but can be necessary for other Spring Boot applications if they need to actually write in the filesystem.
To reduce Tomcat startup time we added a system property pointing to "/dev/urandom" as a source of entropy.
if you are using boot2docker you need to run it first before you do anything with the Docker command line or with the build tools (it runs a daemon process that handles the work for you in a virtual machine).
To build the image you can use some tooling for Maven or Gradle from the community (big thanks to Palantir and Spotify for making those tools available).

Build a Docker Image with Maven
In the Maven pom.xml you should add a new plugin like this (see the plugin documentation for more options): :

### You can add below plugin in pom.xml to generate images


```xml
<properties>
   <docker.image.prefix>springio</docker.image.prefix>
</properties>
<build>
    <plugins>
        <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>dockerfile-maven-plugin</artifactId>
            <version>1.3.6</version>
            <configuration>
                <repository>${docker.image.prefix}/${project.artifactId}</repository>
                <buildArgs>
                    <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
                </buildArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```
**The configuration specifies 3 things:**
> The repository with the image name, which will end up here as
> shl/mock
> The name of the jar file, exposing the Maven configuration as a build argument for docker.

Optionally, the image tag, which ends up as latest if not specified. It can be set to the artifact id if desired.

Before proceeding with the following steps (which use Docker’s CLI tools), make sure Docker is properly running by typing docker ps. If you get an error message, something may not be set up correctly. **Using a Mac?** Add ```$(boot2docker shellinit 2> /dev/null)``` to the bottom of your *.bash_profile* (or similar env-setting configuration file) and refresh your shell to ensure proper environment variables are configured.

You can build a tagged docker image using the command line like this:
```sh
$ ./mvnw install dockerfile:build
```

And you can push the image to dockerhub with
```sh
./mvnw dockerfile:push.
```

You don’t have to push your newly minted Docker image to actually run it. Moreover the "push" command will fail if you aren’t a member of the "springio" organization on Dockerhub. Change the build configuration and the command line to your own username instead of "springio" to make it actually work.

you can make ```dockerfile:push``` automatically run in the install or deploy lifecycle phases by adding it to the plugin configuration.
### pom.xml
```xml
<executions>
	<execution>
		<id>default</id>
		<phase>install</phase>
		<goals>
			<goal>build</goal>
			<goal>push</goal>
		</goals>
	</execution>
</executions>
```

References: [spring-boot-docker](https://spring.io/guides/gs/spring-boot-docker/#initial)
