package com.example.Trailers.trailers.service;

import com.example.Trailers.profile.mapper.ProfileMapper;
import com.example.Trailers.trailers.dto.TrailerResponse;
import com.example.Trailers.trailers.mapper.TrailerMapper;
import com.example.Trailers.trailers.model.Trailers;
import com.example.Trailers.trailers.repo.TrailersRepo;
import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.user.repo.UserAccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrailerServiceTest {

    @Mock
    private TrailersRepo trailersRepo;

    @Mock
    private UserAccountRepo userAccountRepo;

    @Mock
    private TrailerMapper trailerMapper;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private TrailerService trailerService;

    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount();
        // Set up user account properties as needed for the tests
    }

    @Test
    void viewTrailersByTitle_shouldReturnListOfTrailers() {
        // Given
        Trailers trailer = new Trailers();
        trailer.setTitle("Test Trailer");
        List<Trailers> trailers = Collections.singletonList(trailer);
        TrailerResponse trailerResponse = new TrailerResponse();
        trailerResponse.setTitle("Test Trailer");

        when(trailersRepo.findByTitleContainingIgnoreCase(anyString())).thenReturn(trailers);
        when(trailerMapper.toTrailerResponse(trailer, null)).thenReturn(trailerResponse);

        // When
        List<TrailerResponse> result = trailerService.viewTrailersByTitle("Test", userAccount);

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Trailer", result.get(0).getTitle());
    }
}
