package com.example.Ej1;

import com.example.Ej1.domain.Booking;
import com.example.Ej1.domain.Space;
import com.example.Ej1.domain.Tag;
import com.example.Ej1.dto.CityTagCountDto;
import com.example.Ej1.dto.MostProfitSpacesDto;
import com.example.Ej1.dto.MostUsedTagDto;
import com.example.Ej1.service.BookingService;
import com.example.Ej1.service.SpaceService;
import com.example.Ej1.service.TagService;
import com.example.Ej1.service.VenueService;

import java.util.List;

public class MainConsultas {
    public static void main(String[] args) {
        VenueService venueService = new VenueService();
        SpaceService spaceService = new SpaceService();
        BookingService bookingService = new BookingService();
        TagService tagService = new TagService();

        System.out.println("[ Consulta 1: ]");
        System.out.println(venueService.getVenuesByCity());

        System.out.println("[ Consulta 2: ]");
        System.out.println(venueService.getTop5VenuesWithMostSpaces());

        System.out.println("[ Consulta 3: ]");
        System.out.println(venueService.getVenuesConfirmed());

        System.out.println("[ Consulta 4: ]");
        int minCapacity = 25;
        double maxPrice = 50.0;
        List<Space> activeSpaces = spaceService.findActiveSpaces(minCapacity, maxPrice);
        activeSpaces.forEach(System.out::println);

        System.out.println("[ Consulta 5: ]");
        List<Space> neverReservedSpaces = spaceService.getNeverReservedSpaces();
        neverReservedSpaces.forEach(System.out::println);

        System.out.println("[ Consulta 6: ]");
        List<Booking> confirmados = bookingService.getConfirmedBookingsByVenueAndRange();
        confirmados.forEach(System.out::println);

        System.out.println("[ Consulta 7: ]");
        List<MostProfitSpacesDto> top3Spaces = spaceService.getMostProfitSpaces();
        top3Spaces.forEach(System.out::println);

        System.out.println("[ Consulta 8: ]");
        List<MostUsedTagDto> mostUsedTagDtos = tagService.getMostUsedTags();
        mostUsedTagDtos.forEach(System.out::println);

        System.out.println("[ Consulta 9: ]");
        List<Tag> tags = tagService.getTagByText();
        tags.forEach(System.out::println);

        System.out.println("[ Consulta 10: ]");
        List<CityTagCountDto> cityTagCountDtos = tagService.getCountSpaces();
        cityTagCountDtos.forEach(System.out::println);
    }

}
