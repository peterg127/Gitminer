# Git Repository Mining Tool

## Project Overview

This project is part of the course **Architecture and Integration of Software Systems 2024/2025**. The objective is to develop a Git repository mining tool that loads, processes, and analyzes data from GitHub and Bitbucket repositories using a three-microservice architecture.

---

## Architecture

The application is divided into three microservices:

- **GitHubMiner**: Extracts commits, issues, and comments from GitHub's REST API and sends them to GitMiner using a common data model.
- **BitbucketMiner**: Extracts data from Bitbucket's REST API and sends it to GitMiner.
- **GitMiner**: Receives and stores data in an H2 in-memory database, and exposes a REST API to access projects, commits, issues, and comments.
