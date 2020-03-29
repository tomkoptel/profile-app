package com.olderwold.profile.domain

import org.junit.Test

class ProfileTest {
    @Test
    fun `given profile with firstName=null and lastName=null`() {
        assert(Profile().fullName() == null)
    }

    @Test
    fun `given profile with firstName=Jhon and lastName=null`() {
        assert(Profile(firstName = "Jhon").fullName() == "Jhon")
    }

    @Test
    fun `given profile with firstName=null and lastName=Doe`() {
        assert(Profile(lastName = "Doe").fullName() == "Doe")
    }

    @Test
    fun `given profile with firstName=Jhon and lastName=Doe`() {
        assert(Profile(firstName = "Jhon", lastName = "Doe").fullName() == "Jhon Doe")
    }
}
