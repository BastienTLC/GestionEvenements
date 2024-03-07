package main

import (
	"database/sql"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
	_ "github.com/lib/pq"
)

type Message struct {
	ID       int    `json:"id"`
	EventID  int    `json:"event_id"`
	MemberID int    `json:"member_id"`
	Message  string `json:"message"`
}

var db *sql.DB

func main() {
	connStr := "user=postgres password=Bastien0705 dbname=evenement sslmode=disable host=bastientlc.freeboxos.fr port=32771"
	var err error
	db, err = sql.Open("postgres", connStr)
	if err != nil {
		panic(err)
	}
	defer db.Close()

	router := gin.Default()

	router.GET("/messages", getMessages)
	router.GET("/messages")
	router.GET("/messages/:id", getMessage)
	router.POST("/messages", createMessage)
	router.PUT("/messages/:id", updateMessage)
	router.DELETE("/messages/:id", deleteMessage)

	router.Run(":8080")
}

func getMessages(c *gin.Context) {
	rows, err := db.Query("SELECT * FROM messages")
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	defer rows.Close()

	var messages []Message
	for rows.Next() {
		var message Message
		err := rows.Scan(&message.ID, &message.EventID, &message.MemberID, &message.Message)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}
		messages = append(messages, message)
	}

	c.JSON(http.StatusOK, messages)
}

func getMessage(c *gin.Context) {
	id := c.Param("id")
	row := db.QueryRow("SELECT * FROM messages WHERE id=$1", id)

	var message Message
	err := row.Scan(&message.ID, &message.EventID, &message.MemberID, &message.Message)
	if err != nil {
		if err == sql.ErrNoRows {
			c.JSON(http.StatusNotFound, gin.H{"error": "Message not found"})
		} else {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		}
		return
	}

	c.JSON(http.StatusOK, message)
}

func createMessage(c *gin.Context) {
	var message Message
	if err := c.BindJSON(&message); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	sqlStatement := `INSERT INTO messages (event_id, member_id, message) VALUES ($1, $2, $3) RETURNING id`
	err := db.QueryRow(sqlStatement, message.EventID, message.MemberID, message.Message).Scan(&message.ID)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusCreated, gin.H{"message": "Message created", "id": message.ID})
}
func updateMessage(c *gin.Context) {
	id := c.Param("id")
	var message Message
	if err := c.BindJSON(&message); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	query := `UPDATE messages SET event_id=$1, member_id=$2, message=$3 WHERE id=$4 RETURNING id`
	err := db.QueryRow(query, message.EventID, message.MemberID, message.Message, id).Scan(&message.ID)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Message updated", "id": message.ID})
}

func deleteMessage(c *gin.Context) {
	id := c.Param("id")

	query := `DELETE FROM messages WHERE id=$1`
	res, err := db.Exec(query, id)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	count, err := res.RowsAffected()
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": fmt.Sprintf("%d message(s) deleted", count)})
}
