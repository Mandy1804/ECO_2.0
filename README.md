# 🌱 EcoDescarte

> Plataforma digital para orientar a população sobre o descarte correto de resíduos eletrônicos, promovendo sustentabilidade e consciência ambiental.

![Badge](https://img.shields.io/badge/Status-Conclu%C3%ADdo-brightgreen)
![Badge](https://img.shields.io/badge/Java-17-orange)
![Badge](https://img.shields.io/badge/Spring%20Boot-3.5.7-green)
![Badge](https://img.shields.io/badge/Web-Responsivo-blue)

---

## 📱 Acesse o App

🔗 [EcoDescarte Web App](https://mandy1804.github.io/ECO_2.0/)

---

## 📋 Sobre o Projeto

O **EcoDescarte** é uma solução tecnológica desenvolvida como projeto interdisciplinar na **UniCesumar**, alinhada aos Objetivos de Desenvolvimento Sustentável (ODS) da ONU:

- 🏙️ **ODS 11** — Cidades e Comunidades Sustentáveis
- ♻️ **ODS 12** — Consumo e Produção Responsáveis
- 🌍 **ODS 13** — Ação Contra a Mudança Global do Clima

---

## ✨ Funcionalidades

- 📍 **Mapa Interativo** — Localização de pontos de descarte em Maringá com geolocalização real
- 📅 **Agendamento de Coleta** — Solicitação de coleta domiciliar de resíduos eletrônicos
- 📚 **Educação Ambiental** — Conteúdos sobre descarte consciente
- 🌿 **Calculadora de Impacto** — Estimativa de CO₂ evitado pelo descarte correto

---

## 🛠️ Tecnologias Utilizadas

### Front-end
- HTML5, CSS3, JavaScript
- Leaflet.js (mapa interativo)
- Design responsivo (mobile-first)

### Back-end
- Java 17
- Spring Boot 3.5.7
- Spring Data JPA
- PostgreSQL
- Docker

### Testes
- JUnit 5
- Mockito

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 17+
- Maven
- Docker

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/Mandy1804/ECO_2.0

# 2. Entre na pasta do back-end
cd ECO_2.0/backend

# 3. Suba o banco de dados PostgreSQL via Docker
docker-compose up -d

# 4. Execute o back-end Spring Boot
C:\apache-maven-3.9.16\bin\mvn.cmd spring-boot:run

# 5. Acesse o front-end
https://mandy1804.github.io/ECO_2.0/
```

### Executar os testes
```bash
mvn test
```

---

## 📁 Estrutura do Projeto

```
ECO_2.0/
├── backend/
│   ├── src/
│   │   ├── main/
│   │      ├── java/com/ecodescarte/backend/
│   │      │   ├── controller/
│   │      │   ├── exception/
│   │      │   ├── model/
│   │      │   ├── repository/
│   │      │   └── BackendApplication.java
│   │      └── resources/
│   │          ├── application.properties
│   │          └── data.sql
│   │   
│   └── test/
│       └── java/com/ecodescarte/backend/
│           ├── AgendamentoServiceTest.java
│           ├── BackendApplicationTests.java
│           ├── PontoDescarteServiceTest.java
│   ├── docker-compose.yml
│   └── pom.xml
├── css/
├── js/
├── imagem/
├── index.html
├── mapa.html
├── agendamento.html
├── educacional.html
└── impacto.html

```
---
## 🔌 Endpoints da API

### Pontos de Descarte

| Método | Endpoint |
|---------|----------|
| GET | /api/pontos-descarte |
| GET | /api/pontos-descarte/{id} |
| POST | /api/pontos-descarte |

### Agendamentos

| Método | Endpoint |
|---------|----------|
| GET | /api/agendamentos |
| POST | /api/agendamentos |

---

## 👥 Equipe

| Nome | RA |
|------|----|
| Sabrina de Souza Laurindo | 24408832-2 |
| Amanda Rodrigues Camargo  | 24440170-2 |

---

## 📄 Licença

Projeto acadêmico desenvolvido para a disciplina de **Análise e Desenvolvimento de Sistemas** — UniCesumar 2026.
