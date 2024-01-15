package me.dio.credit.application.system.controller

import me.dio.credit.application.system.dto.request.CustomerDTO
import me.dio.credit.application.system.dto.request.CustomerUpdateDTO
import me.dio.credit.application.system.dto.response.CustomerView
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.service.impl.CustomerServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerController (
    private val customerServiceImpl: CustomerServiceImpl
){

    @PostMapping
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerView> {
        val savedCustomer: Customer = this.customerServiceImpl.save(customerDTO.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerView(savedCustomer))
    }

    @GetMapping("/findById/{id}")
    fun getCustomersById(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerServiceImpl.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer))
    }
    @GetMapping("/findAll")
    fun getCustomers(): ResponseEntity<List<CustomerView>> {
        val customers: List<Customer> = this.customerServiceImpl.findAll()
        val customerViews: List<CustomerView> = customers.map { customer -> CustomerView(customer) }
        return ResponseEntity.status(HttpStatus.OK).body(customerViews)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCustomer(@PathVariable id: Long) {
        //TODO Implementar o safe delete
    }

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId") id: Long,
                       @RequestBody customerUpdate: CustomerUpdateDTO
    ): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerServiceImpl.findById(id)
        val customerToUpdate = customerUpdate.toEntity(customer)
        val customerUpdated: Customer = this.customerServiceImpl.save(customerToUpdate)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customerUpdated))
    }

}