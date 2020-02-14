package com.web.rest;

import com.RenseignementApp;
import com.domain.Profile;
import com.repository.ProfileRepository;
import com.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.domain.enumeration.Musictype;
/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = RenseignementApp.class)
public class ProfileResourceIT {

    private static final String DEFAULT_SEXE = "AAAAAAAAAA";
    private static final String UPDATED_SEXE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_RESIDENT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENT_STATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final String DEFAULT_ARTIST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ARTIST_NAME = "BBBBBBBBBB";

    private static final Musictype DEFAULT_CATEGORIE = Musictype.CHANT;
    private static final Musictype UPDATED_CATEGORIE = Musictype.SLAM;

    private static final String DEFAULT_OTHER_MUSIC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_MUSIC_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EVENT_PARTICIPATION = false;
    private static final Boolean UPDATED_EVENT_PARTICIPATION = true;

    private static final String DEFAULT_EVENT_PARTICIPATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_PARTICIPATION_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXPERIENCE_TIME = 1;
    private static final Integer UPDATED_EXPERIENCE_TIME = 2;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileRepository);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .sexe(DEFAULT_SEXE)
            .age(DEFAULT_AGE)
            .residentState(DEFAULT_RESIDENT_STATE)
            .phone(DEFAULT_PHONE)
            .artistName(DEFAULT_ARTIST_NAME)
            .categorie(DEFAULT_CATEGORIE)
            .otherMusicType(DEFAULT_OTHER_MUSIC_TYPE)
            .eventParticipation(DEFAULT_EVENT_PARTICIPATION)
            .eventParticipationName(DEFAULT_EVENT_PARTICIPATION_NAME)
            .experienceTime(DEFAULT_EXPERIENCE_TIME);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .sexe(UPDATED_SEXE)
            .age(UPDATED_AGE)
            .residentState(UPDATED_RESIDENT_STATE)
            .phone(UPDATED_PHONE)
            .artistName(UPDATED_ARTIST_NAME)
            .categorie(UPDATED_CATEGORIE)
            .otherMusicType(UPDATED_OTHER_MUSIC_TYPE)
            .eventParticipation(UPDATED_EVENT_PARTICIPATION)
            .eventParticipationName(UPDATED_EVENT_PARTICIPATION_NAME)
            .experienceTime(UPDATED_EXPERIENCE_TIME);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testProfile.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testProfile.getResidentState()).isEqualTo(DEFAULT_RESIDENT_STATE);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.getArtistName()).isEqualTo(DEFAULT_ARTIST_NAME);
        assertThat(testProfile.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testProfile.getOtherMusicType()).isEqualTo(DEFAULT_OTHER_MUSIC_TYPE);
        assertThat(testProfile.isEventParticipation()).isEqualTo(DEFAULT_EVENT_PARTICIPATION);
        assertThat(testProfile.getEventParticipationName()).isEqualTo(DEFAULT_EVENT_PARTICIPATION_NAME);
        assertThat(testProfile.getExperienceTime()).isEqualTo(DEFAULT_EXPERIENCE_TIME);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].residentState").value(hasItem(DEFAULT_RESIDENT_STATE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].artistName").value(hasItem(DEFAULT_ARTIST_NAME)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].otherMusicType").value(hasItem(DEFAULT_OTHER_MUSIC_TYPE)))
            .andExpect(jsonPath("$.[*].eventParticipation").value(hasItem(DEFAULT_EVENT_PARTICIPATION.booleanValue())))
            .andExpect(jsonPath("$.[*].eventParticipationName").value(hasItem(DEFAULT_EVENT_PARTICIPATION_NAME)))
            .andExpect(jsonPath("$.[*].experienceTime").value(hasItem(DEFAULT_EXPERIENCE_TIME)));
    }
    
    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.residentState").value(DEFAULT_RESIDENT_STATE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.artistName").value(DEFAULT_ARTIST_NAME))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.otherMusicType").value(DEFAULT_OTHER_MUSIC_TYPE))
            .andExpect(jsonPath("$.eventParticipation").value(DEFAULT_EVENT_PARTICIPATION.booleanValue()))
            .andExpect(jsonPath("$.eventParticipationName").value(DEFAULT_EVENT_PARTICIPATION_NAME))
            .andExpect(jsonPath("$.experienceTime").value(DEFAULT_EXPERIENCE_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .sexe(UPDATED_SEXE)
            .age(UPDATED_AGE)
            .residentState(UPDATED_RESIDENT_STATE)
            .phone(UPDATED_PHONE)
            .artistName(UPDATED_ARTIST_NAME)
            .categorie(UPDATED_CATEGORIE)
            .otherMusicType(UPDATED_OTHER_MUSIC_TYPE)
            .eventParticipation(UPDATED_EVENT_PARTICIPATION)
            .eventParticipationName(UPDATED_EVENT_PARTICIPATION_NAME)
            .experienceTime(UPDATED_EXPERIENCE_TIME);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfile)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testProfile.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testProfile.getResidentState()).isEqualTo(UPDATED_RESIDENT_STATE);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getArtistName()).isEqualTo(UPDATED_ARTIST_NAME);
        assertThat(testProfile.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testProfile.getOtherMusicType()).isEqualTo(UPDATED_OTHER_MUSIC_TYPE);
        assertThat(testProfile.isEventParticipation()).isEqualTo(UPDATED_EVENT_PARTICIPATION);
        assertThat(testProfile.getEventParticipationName()).isEqualTo(UPDATED_EVENT_PARTICIPATION_NAME);
        assertThat(testProfile.getExperienceTime()).isEqualTo(UPDATED_EXPERIENCE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
