package com.olderwold.profile.data

import com.olderwold.profile.domain.Profile
import org.junit.Test

class ProfileMapperTest {
    private val mapper = ProfileMapper()

    @Test
    fun `mapper maps null profileDTO to null`() {
        val domain = mapper.toDomain(null)

        assert(domain == Profile())
    }

    @Test
    fun `maps firstName to firstName`() {
        val character = mapper.toDomain(ProfileDTO(firstName = "Bilbo"))

        assert(character.firstName == "Bilbo")
    }

    @Test
    fun `maps nullable firstName to null`() {
        val character = mapper.toDomain(ProfileDTO())

        assert(character.firstName == null)
    }

    @Test
    fun `maps lastName to lastName`() {
        val character = mapper.toDomain(ProfileDTO(lastName = "Baggins"))

        assert(character.lastName == "Baggins")
    }

    @Test
    fun `maps nullable lastName to null`() {
        val character = mapper.toDomain(ProfileDTO())

        assert(character.lastName == null)
    }

    @Test
    fun `maps avatarUrl to avatarUrl`() {
        val character = mapper.toDomain(ProfileDTO(avatarUrl = "http://localhost"))

        assert(character.avatarUrl == "http://localhost")
    }

    @Test
    fun `maps avatarUrl to profileUrl`() {
        val character = mapper.toDomain(ProfileDTO(profileUrl = "http://localhost"))

        assert(character.profileUrl == "http://localhost")
    }
}
