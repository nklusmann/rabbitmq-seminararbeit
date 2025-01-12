# Seminararbeit RabbitMQ

## Überblick
Bei diesem Projekt handelt es sich um die Implementierung der in der Seminararbeit beschriebenen IoT-Architektur. Die folgenden Kapitel bieten einen Überblick über die Struktur des Projektes, eine Anleitung zur Installation auf dem lokalen Gerät und weitere nützliche Informationen zum Testen des Systems.

## Inhaltsverzeichnis
- [Projektstruktur](#projektstruktur)
- [Installation](#installation)
  - [1. Docker Desktop](#1-docker-desktop)
  - [2. Repository klonen](#2-repository-klonen)
  - [3. Docker Images und Container erstellen](#3-docker-images-und-container-erstellen)
  - [4. Docker Container stoppen/löschen](#3-docker-container-stoppenlöschen)
- [Testen](#testen)
  - [Nachrichten Publishen](#nachrichten-publishen)
  - [RabbitMQ UI Management](#rabbitmq-ui-management)

## Projektstruktur
Dieser Abschnitt soll einen Überblick über die wichtigsten Verzeichnisse und Dateien geben, zusammen mit einer kurzen Beschreibung ihrer Rolle.
```bash
.
├── applications  # Enthält Publisher- und Consumer-Anwendungscode
│   ├── rabbitmq-consumer
│   │   ├── ...
│   │   ├── docker  # Enthält JAR-File der Anwendung    
│   │   ├── src                          
│   │   │   ├── main                     
│   │   │   │   ├── java                 
│   │   │   │   │   └── com              
│   │   │   │   │       └── example    
│   │   │   │   │           └── rabbitmq_consumer
│   │   │   │   │               ├── RabbitmqConsumerApplication.java  # Hauptklasse der Anwendung
│   │   │   │   │               ├── configs
│   │   │   │   │               │   └── RabbitMQConfig.java  # Enthält Deklarierung von Quorum Queue & DLX für das Upstream RabbitMQ Cluster
│   │   │   │   │               ├── constants
│   │   │   │   │               │   └── RabbitMQConstant.java
│   │   │   │   │               └── listener
│   │   │   │   │                   └── RabbitMQConsumer.java  # Klasse zum Konsumieren von Nachrichten aus Quorum Queue
│   │   │   │   └── resources 
│   │   │   │       └── application.properties  # Enthält Adressen der Upstream RabbitMQ Broker
│   │   │   └── ...
│   │   ├── ...
│   │   ├── Dockerfile  # Dockerfile für Consumer-Image
│   │   ├── ...
│   └── rabbitmq-publisher     
│       ├── ...
│       ├── docker  # Enthält JAR-File der Anwendung    
│       ├── src                 
│       │   ├── main            
│       │   │   ├── java        
│       │   │   │   └── com     
│       │   │   │       └── example
│       │   │   │           └── rabbitmq_publisher  
│       │   │   │               ├── RabbitmqPublisherApplication.java  # Hauptklasse der Anwendung
│       │   │   │               ├── configs  
│       │   │   │               │   └── RabbitMQConfig.java  # Enthält Template zum Publishen von Nachrichten mittels Publisher Confirm
│       │   │   │               ├── constants  
│       │   │   │               │   ├── PathConstants.java
│       │   │   │               │   └── RabbitMQConstants.java
│       │   │   │               ├── controller  
│       │   │   │               │   └── StatusReportController.java  # REST Controller zum Publishen von Nachrichten
│       │   │   │               ├── records  
│       │   │   │               │   └── StatusReportRequestDto.java 
│       │   │   │               └── services  
│       │   │   │                   └── RabbitMQProducer.java  # Klasse zum Publishen von Nachrichten. Enthält Callback, falls Nachricht nicht erfolgreich published werden kann.
│       │   │   └── resources
│       │   │       └── application.properties  # Enthält Adresse des Local RabbitMQ und Publisher Confirm Konfiguration.
│       │   └── ...
│       ├── ...
│       ├── Dockerfile  # Dockerfile für Publisher-Image
│       ├── ...
├── configs  # Konfigurationsdateien für RabbitMQ Broker
│   ├── local-rabbit              
│   │   ├── advanced.config  # Enthält Deklarierung des Shovel und der Queue für Local RabbitMQ
│   │   └── enabled_plugins.txt  # Shovel und Shovel Management Plugin
│   ├── upstream-rabbit-1 
│   │   └── rabbitmq.conf  # Enthält Peer Discovery Konfiguration für Upstream RabbitMQ Cluster
│   ├── upstream-rabbit-2        
│   │   └── rabbitmq.conf  # Enthält Peer Discovery Konfiguration für Upstream RabbitMQ Cluster
│   └── upstream-rabbit-3  
│       └── rabbitmq.conf  # Enthält Peer Discovery Konfiguration für Upstream RabbitMQ Cluster
└── compose.yml  # Docker-Compose-Datei zum Starten der Anwendungen und Brokern als Container
```

## Installation
### 1. Docker Desktop
Die beiden Publisher und Consumer Anwendungen als auch die RabbitMQ Broker werden als Docker Container bereitgestellt, weshalb zuallererst Docker Desktop heruntergeladen werden muss. 
Klicke dazu auf folgenden Link, falls noch nicht heruntergeladen: [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### 2. Repository klonen
Führe den folgenden Befehl im gewünschten Verzeichnis im Terminal oder in der Eingabeaufforderung bzw. PowerShell aus, um das Repository zu klonen:
```bash
git clone https://github.com/nklusmann/rabbitmq-seminararbeit.git
```

### 3. Docker Images und Container erstellen
Starte Docker Desktop. Navigiere mittels Terminal oder Eingabeaufforderung bzw. PowerShell in das Root-Verzeichnis des Projektes und führe 
folgenden Befehl aus um die einzelnen Komponenten der IoT-Architektur als Docker Container zu erstellen:
```bash
docker compose up
```
Dies könnte im ersten Durchlauf etwas dauern, da die Images generiert bzw. heruntergeladen werden müssen.
Damit ist die Installation abgeschlossen, sowohl die Anwendungen als auch die Broker gestartet und bereit zum Testen.

### 4. Docker Container stoppen/löschen
Zum löschen der Container nutze folgenden Befehl:
```bash
docker compose down
```
Zum stoppen der Container nutze folgenden Befehl:
```bash
docker compose stop
```

## Testen
Die Docker Container exponieren unterschiedliche Ports, welche zum Testen der Implementierung von Vorteil sind.

### Nachrichten Publishen
Um Nachrichten über die Drohne an den Local RabbitMQ zu publishen bietet die Publisher Anwendung einen REST Endpunkt, welcher über eine Swagger UI getriggert werden kann.
Diese ist über folgenden Link erreichbar: [Swagger UI](http://localhost:9999/swagger-ui/index.html)

### RabbitMQ UI Management
Die RabbitMQ Broker bieten jeweils ein UI Management an. Dieses gibt uns einen Einblick in die Broker, die darin existierenden Queues, Exchanges, Connections und vieles mehr.
Das UI Management des Local RabbitMQ ist bswp. über folgenden Link erreichbar: http://localhost:8080 (Username: guest, Password: guest). Für die anderen Broker muss der Port entsprechend angepasst werden.
Alternativ kann man auch über das Docker Desktop Dashboard in der Containerübersicht in der Spalte Port(s) auf die jeweiligen Ports klicken.
