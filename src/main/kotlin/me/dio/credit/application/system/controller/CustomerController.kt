package me.dio.credit.application.system.controller

import me.dio.credit.application.system.dto.CustomerDTO
import me.dio.credit.application.system.dto.CustomerView
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.service.impl.CustomerServiceImpl
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController (
    private val customerServiceImpl: CustomerServiceImpl
){

    @PostMapping
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): String {
        val savedCustomer = this.customerServiceImpl.save(customerDTO.toEntity())
        return "Customer ${savedCustomer.email} saved!"
    }

    @GetMapping("/findById/{id}")
    fun getCustomersById(@PathVariable id: Long): CustomerView {
        val customer: Customer = this.customerServiceImpl.findById(id)
        return CustomerView(customer)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCustomer(@PathVariable id: Long) {
        //TODO Implementar o safe delete
    }

}