FROM golang:1.22 as builder

WORKDIR /app

COPY go.mod .
RUN go mod tidy

# Ajoutez les dépendances manquantes
RUN go get github.com/gin-contrib/cors
RUN go get github.com/gin-gonic/gin
RUN go get github.com/lib/pq

COPY *.go .
RUN go build

EXPOSE 8080

CMD ["./GestionCommentaire"]
