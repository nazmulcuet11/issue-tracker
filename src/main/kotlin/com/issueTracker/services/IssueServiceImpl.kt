//package com.issueTracker.services
//
//import com.issueTracker.models.Issue
//import com.issueTracker.repositories.interfaces.IssueRepository
//import com.issueTracker.services.interfaces.IssueService
//import java.util.Date
//
//class IssueServiceImpl(
//    private val repository: IssueRepository
//): IssueService {
//    override suspend fun getAllIssues(): List<Issue> {
//        return repository.selectAll()
//    }
//
//    override suspend fun getIssueById(id: Int): Issue? {
//        return repository.selectById(id)
//    }
//
//    override suspend fun createIssue(title: String, description: String?): Issue? {
//        val issue = Issue(
//            id = 0,
//            title = title,
//            description = description,
//            createdAt = Date()
//        )
//        return repository.insert(issue)
//    }
//}
