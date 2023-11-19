package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.common.models.PageResponse;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.handler.MySortHandler;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowQuery;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.follow.FollowUseCaseService;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class FollowsController implements FollowsAPI {
    @NonNull
    final FollowUseCaseService followUseCaseService;
    @NonNull
    final FollowModelMapper followModelMapper;
    @Override
    public void save(FollowRequest followRequest) {
        Follow follow = followModelMapper.toDto(followRequest);
        followUseCaseService.save(follow);
    }

    @Override
    public ResponseEntity<PageResponse<FollowResponse>> findAll(String search, LocalDate fromDate,
                                                                LocalDate toDate, String sort,
                                                                Integer currentPage, Integer pageFollow) {
        FollowQuery followQuery = FollowQuery.builder()
                .search(search)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageFollow, MySortHandler.of(sort));

        Page<Follow> followPage = followUseCaseService.findAll(followQuery, pageRequest);

        PageResponse<FollowResponse> followResponsePage = new PageResponse<>(followPage.map(followModelMapper::toModel));
        return new ResponseEntity<>(followResponsePage, HttpStatus.OK);
    }
}
