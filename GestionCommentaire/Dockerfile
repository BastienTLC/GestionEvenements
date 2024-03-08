FROM golang:1.22 as builder

WORKDIR /app

COPY go.mod .
COPY go.sum .
RUN go mod download

COPY *.go .

RUN go build

EXPOSE 8080

CMD ["./GestionCommentaire"]
