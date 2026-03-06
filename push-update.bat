@echo off
cd /d "%~dp0"

echo Adding files...
git add .

echo Committing...
git commit -m "feat: add docker deployment and docs

- Docker Compose configuration
- Dockerfile for backend and frontend
- Nginx reverse proxy config
- Deployment documentation"

echo Pushing to GitHub...
git push origin main

echo Done!
pause
