package au.com.heal.martin.mapinfo;

import java.util.ArrayList;
import java.util.List;

import au.com.heal.martin.mapinfo.domain.entities.AreaEntity;
import au.com.heal.martin.mapinfo.domain.entities.AreaPointEntity;
import au.com.heal.martin.mapinfo.domain.entities.LocationEntity;
import au.com.heal.martin.mapinfo.domain.entities.LocationPointEntity;
import au.com.heal.martin.mapinfo.domain.entities.TrackEntity;
import au.com.heal.martin.mapinfo.domain.entities.TrackPointEntity;

public final class TestDataUtil {
    private TestDataUtil() {}

    public static LocationPointEntity createTestLocationPoint(final double latitude, final double longitude) {
        return LocationPointEntity.builder()
        .latitude(latitude)
        .longitude(longitude)
        .build();
    }

    public static AreaPointEntity createTestAreaPoint(final double latitude, final double longitude) {
        return AreaPointEntity.builder()
        .latitude(latitude)
        .longitude(longitude)
        .build();
    }

    public static TrackPointEntity createTestTrackPoint(final double latitude, final double longitude) {
        return TrackPointEntity.builder()
        .latitude(latitude)
        .longitude(longitude)
        .build();
    }

    public static LocationEntity createTestLocation1() {
        LocationPointEntity point = TestDataUtil.createTestLocationPoint(-37.814233512, 144.963138807);

        return LocationEntity.builder()
        .name("Melbourne")
        .description("Melbourne, Victoria, Australia.")
        .point(point)
        .build();
    }

    public static LocationEntity createTestLocation2() {
        LocationPointEntity point = TestDataUtil.createTestLocationPoint(-33.857034235, 151.215075878);

        return LocationEntity.builder()
        .name("Sydney Opera House")
        .description("Sydney Opera House performing arts theater.")
        .point(point)
        .build();
    }

    public static LocationEntity createTestLocation3() {
        LocationPointEntity point = TestDataUtil.createTestLocationPoint(-27.475030728, 153.030095007);

        return LocationEntity.builder()
        .name("Brisbane Botanic Gardens")
        .description("City botanic gardens in Brisbane, Queensland.")
        .point(point)
        .build();
    }

    public static TrackEntity createTestTrack1() {
        TrackEntity track = TrackEntity.builder()
        .name("Collins St")
        .description("Collins Street Melbourne.")
        .build();

        List<TrackPointEntity> points = new ArrayList<>();

        TrackPointEntity trackPointEntity1 = TestDataUtil.createTestTrackPoint(-37.820640807, 144.941401533);
        trackPointEntity1.setTrack(track);
        points.add(trackPointEntity1);

        TrackPointEntity trackPointEntity2 = TestDataUtil.createTestTrackPoint(-37.821407644, 144.945846615);
        trackPointEntity2.setTrack(track);
        points.add(trackPointEntity2);

        TrackPointEntity trackPointEntity3 = TestDataUtil.createTestTrackPoint(-37.813321828, 144.973760363);
        trackPointEntity3.setTrack(track);
        points.add(trackPointEntity3);

        track.setPoints(points);

        return track;
    }

    public static TrackEntity createTestTrack2() {
        TrackEntity track = TrackEntity.builder()
        .name("Harbour Bridge")
        .description("Harbour Bridge in Sydney.")
        .build();

        List<TrackPointEntity> points = new ArrayList<>();

        TrackPointEntity trackPointEntity1 = TestDataUtil.createTestTrackPoint(-33.846493037, 151.212337605);
        trackPointEntity1.setTrack(track);
        points.add(trackPointEntity1);

        TrackPointEntity trackPointEntity2 = TestDataUtil.createTestTrackPoint(-33.849380039, 151.212423436);
        trackPointEntity2.setTrack(track);
        points.add(trackPointEntity2);

        TrackPointEntity trackPointEntity3 = TestDataUtil.createTestTrackPoint(-33.854547885, 151.209290616);
        trackPointEntity3.setTrack(track);
        points.add(trackPointEntity3);

        TrackPointEntity trackPointEntity4 = TestDataUtil.createTestTrackPoint(-33.860143062, 151.206071965);
        trackPointEntity4.setTrack(track);
        points.add(trackPointEntity4);

        track.setPoints(points);

        return track;
    }

    public static TrackEntity createTestTrack3() {
        TrackEntity track = TrackEntity.builder()
        .name("Sydney St - N Quay Ferry")
        .description("Path of the Sydney street - North Quay ferry.")
        .build();

        List<TrackPointEntity> points = new ArrayList<>();

        TrackPointEntity trackPointEntity1 = TestDataUtil.createTestTrackPoint(-27.472525297, 153.039058249);
        trackPointEntity1.setTrack(track);
        points.add(trackPointEntity1);

        TrackPointEntity trackPointEntity2 = TestDataUtil.createTestTrackPoint(-27.473477189, 153.040292065);
        trackPointEntity2.setTrack(track);
        points.add(trackPointEntity2);

        TrackPointEntity trackPointEntity3 = TestDataUtil.createTestTrackPoint(-27.474766991, 153.042164246);
        trackPointEntity3.setTrack(track);
        points.add(trackPointEntity3);

        track.setPoints(points);

        return track;
    }

    public static AreaEntity createTestArea1() {
        AreaEntity area = AreaEntity.builder()
            .name("Melbourne CBD")
            .description("Melbourne CBD business district")
            .build();

        List<AreaPointEntity> points = new ArrayList<>();

        AreaPointEntity areaPointEntity1 = TestDataUtil.createTestAreaPoint(-37.813187012, 144.951474734);
        areaPointEntity1.setArea(area);
        points.add(areaPointEntity1);

        AreaPointEntity areaPointEntity2 = TestDataUtil.createTestAreaPoint(-37.807355343, 144.971194331);
        areaPointEntity2.setArea(area);
        points.add(areaPointEntity2);

        AreaPointEntity areaPointEntity3 = TestDataUtil.createTestAreaPoint(-37.815305964, 144.974949423);
        areaPointEntity3.setArea(area);
        points.add(areaPointEntity3);

        AreaPointEntity areaPointEntity4 = TestDataUtil.createTestAreaPoint(-37.821086155, 144.955079622);
        areaPointEntity4.setArea(area);
        points.add(areaPointEntity4);

        area.setPoints(points);

        return area;
    }

    public static AreaEntity createTestArea2() {
        AreaEntity area = AreaEntity.builder()
            .name("Prince Alfred Park")
            .description("Prince Alfred Park in Sydney.")
            .build();

        List<AreaPointEntity> points = new ArrayList<>();

        AreaPointEntity areaPointEntity1 = TestDataUtil.createTestAreaPoint(-33.889115893, 151.202664000);
        areaPointEntity1.setArea(area);
        points.add(areaPointEntity1);

        AreaPointEntity areaPointEntity2 = TestDataUtil.createTestAreaPoint(-33.888256440, 151.202921492);
        areaPointEntity2.setArea(area);
        points.add(areaPointEntity2);

        AreaPointEntity areaPointEntity3 = TestDataUtil.createTestAreaPoint(-33.886052104, 151.206789237);
        areaPointEntity3.setArea(area);
        points.add(areaPointEntity3);

        AreaPointEntity areaPointEntity4 = TestDataUtil.createTestAreaPoint(-33.888608238, 151.206204516);
        areaPointEntity4.setArea(area);
        points.add(areaPointEntity4);

        AreaPointEntity areaPointEntity5 = TestDataUtil.createTestAreaPoint(-33.888519175, 151.205614430);
        areaPointEntity5.setArea(area);
        points.add(areaPointEntity5);

        AreaPointEntity areaPointEntity6 = TestDataUtil.createTestAreaPoint(-33.889454328, 151.205297929);
        areaPointEntity6.setArea(area);
        points.add(areaPointEntity6);

        area.setPoints(points);

        return area;
    }

    public static AreaEntity createTestArea3() {
        AreaEntity area = AreaEntity.builder()
            .name("Prince Alfred Park")
            .description("Prince Alfred Park in Sydney.")
            .build();

        List<AreaPointEntity> points = new ArrayList<>();

        AreaPointEntity areaPointEntity1 = TestDataUtil.createTestAreaPoint(-27.391796885, 153.225868885);
        areaPointEntity1.setArea(area);
        points.add(areaPointEntity1);

        AreaPointEntity areaPointEntity2 = TestDataUtil.createTestAreaPoint(-27.390526674, 153.228696025);
        areaPointEntity2.setArea(area);
        points.add(areaPointEntity2);

        AreaPointEntity areaPointEntity3 = TestDataUtil.createTestAreaPoint(-27.387562791, 153.230126626);
        areaPointEntity3.setArea(area);
        points.add(areaPointEntity3);

        AreaPointEntity areaPointEntity4 = TestDataUtil.createTestAreaPoint(-27.385233970, 153.229649759);
        areaPointEntity4.setArea(area);
        points.add(areaPointEntity4);

        AreaPointEntity areaPointEntity5 = TestDataUtil.createTestAreaPoint(-27.377793513, 153.235099667);
        areaPointEntity5.setArea(area);
        points.add(areaPointEntity5);

        AreaPointEntity areaPointEntity6 = TestDataUtil.createTestAreaPoint(-27.379094117, 153.237858684);
        areaPointEntity6.setArea(area);
        points.add(areaPointEntity6);

        AreaPointEntity areaPointEntity7 = TestDataUtil.createTestAreaPoint(-27.390224241, 153.241435186);
        areaPointEntity7.setArea(area);
        points.add(areaPointEntity7);

        AreaPointEntity areaPointEntity8 = TestDataUtil.createTestAreaPoint(-27.397119516, 153.239050851);
        areaPointEntity8.setArea(area);
        points.add(areaPointEntity8);

        AreaPointEntity areaPointEntity9 = TestDataUtil.createTestAreaPoint(-27.393550962, 153.227742291);
        areaPointEntity9.setArea(area);
        points.add(areaPointEntity9);

        area.setPoints(points);

        return area;
    }
}
