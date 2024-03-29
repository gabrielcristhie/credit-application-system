package me.dio.credit.application.system.service.impl

import org.springframework.cache.annotation.Cacheable
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.ICustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository
): ICustomerService {
    override fun save(customer: Customer): Customer =
       this.customerRepository.save(customer)

    override fun findById(id: Long): Customer =
        this.customerRepository.findById(id).orElseThrow {
            throw BusinessException("Id $id not found")
    }
    @Cacheable("customersCache")
    override fun findAll(pageable: Pageable): Page<Customer> =
        this.customerRepository.findAll(pageable)

    override fun delete(id: Long) {
        val customer: Customer = this.findById(id)
        this.customerRepository.delete(customer)
    }
}