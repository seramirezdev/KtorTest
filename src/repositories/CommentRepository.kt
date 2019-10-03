package com.seramirezdev.repositories

import com.seramirezdev.dto.CommentDAO
import com.seramirezdev.dto.PlaceDAO
import com.seramirezdev.dto.UserDAO
import com.seramirezdev.entities.Comment
import org.jetbrains.exposed.sql.transactions.transaction

class CommentRepository {

    fun insertCommentToPlace(placeId: Int, userId: Int, comment: Comment): Comment? {
        var createdComment: Comment? = null

        transaction {
            val commentDAO = CommentDAO.new {
                this.comment = comment.comment
                this.rating = comment.rating
                this.place = PlaceDAO.findById(placeId)!!
                this.user = UserDAO.findById(userId)!!
            }

            createdComment = PlaceRepository.toComment(commentDAO)
        }

        return createdComment
    }
}