package me.dio.credit.application.system.controller

import jakarta.validation.Valid
import me.dio.credit.application.system.dto.request.CustomerDTO
import me.dio.credit.application.system.dto.request.CustomerUpdateDTO
import me.dio.credit.application.system.dto.response.CustomerView
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.service.impl.CustomerServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customers")
class CustomerController (
    private val customerServiceImpl: CustomerServiceImpl
){

    @PostMapping
    fun saveCustomer(@Valid @RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerView> {
        val savedCustomer: Customer = this.customerServiceImpl.save(customerDTO.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerView(savedCustomer))
    }

    @GetMapping("/findById/{id}")
    fun getCustomersById(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerServiceImpl.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customer))
    }
    @GetMapping("/findAll")
    fun getCustomers(@PageableDefault(size = 20) paginacao: Pageable): Page<CustomerView> {
        val customers: Page<Customer> = this.customerServiceImpl.findAll(paginacao)
        val customerViews: List<CustomerView> = customers.content.map { customer -> CustomerView(customer) }
        return PageImpl(customerViews, paginacao, customers.totalElements)
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Long) {
        this.customerServiceImpl.delete(id)
    //TODO Implementar o safe delete
    }

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId") id: Long,
                       @RequestBody @Valid customerUpdate: CustomerUpdateDTO
    ): ResponseEntity<CustomerView> {
        val customer: Customer = this.customerServiceImpl.findById(id)
        val customerToUpdate = customerUpdate.toEntity(customer)
        val customerUpdated: Customer = this.customerServiceImpl.save(customerToUpdate)
        return ResponseEntity.status(HttpStatus.OK).body(CustomerView(customerUpdated))
    }
}