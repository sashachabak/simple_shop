package com.tdoft.shop.api.auth;

import com.tdoft.shop.dto.request.IdRequestDto;
import com.tdoft.shop.dto.response.FileResponseDto;
import com.tdoft.shop.dto.response.GoodResponseDto;
import com.tdoft.shop.entity.Good;
import com.tdoft.shop.entity.user.User;
import com.tdoft.shop.exception.GoodNotFoundException;
import com.tdoft.shop.service.AmazonS3Service;
import com.tdoft.shop.service.GoodService;
import com.tdoft.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/good")
@RequiredArgsConstructor
public class GoodsApi {

    private final GoodService goodService;
    private final AmazonS3Service amazonS3Service;
    private final UserService userService;

    @GetMapping("/current-user/liked/count")
    public Integer getCountLikedByCurrentUser() {
        return userService.getCurrentUserOrThrow().getLikedGoods().size();
    }

    @GetMapping("/current-user/selected/count")
    public Integer getCountSelectedByCurrentUser() {
        return userService.getCurrentUserOrThrow().getSelectedGoods().size();
    }

    @GetMapping("/current-user/ordered/count")
    public Integer getCountOrderedByCurrentUser() {
        return userService.getCurrentUserOrThrow().getOrderedGoods().size();
    }

    @GetMapping
    public Page<GoodResponseDto> getAllGoods(@RequestParam(defaultValue = "1") @Positive int page,
                                  @RequestParam(defaultValue = "10") @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Good> goods = goodService.findAll(pageable);
        List<GoodResponseDto> goodResponseDtos = goods
                .stream()
                .map(good -> {
                    String url = amazonS3Service.generateDownloadUrl(good.getImage().getS3BucketFilePath()).toString();
                    FileResponseDto image = new FileResponseDto(good.getImage(), url);
                    return new GoodResponseDto(good, image);
                }).collect(Collectors.toList());

        return new PageImpl<>(goodResponseDtos, pageable, goods.getTotalElements());
    }

    @GetMapping("/current-user/liked")
    public Set<Good> getAllLikedByCurrentUser() {
        return userService.getCurrentUserOrThrow().getLikedGoods();
    }

    @GetMapping("/current-user/ordered")
    public List<Good> getAllOrderedByCurrentUser() {
        return userService.getCurrentUserOrThrow().getOrderedGoods();
    }

    @GetMapping("/current-user/selected")
    public Set<Good> getAllSelectedByCurrentUser() {
        return userService.getCurrentUserOrThrow().getSelectedGoods();
    }

    @PutMapping("/current-user/liked")
    public void addGoodToLiked(@RequestBody IdRequestDto id) {
        Good good = goodService.findById(id.getId()).orElseThrow(() -> new GoodNotFoundException(id.getId()));
        User user = userService.getCurrentUserOrThrow();

        user.getLikedGoods().add(good);
        userService.save(user);
    }

    @DeleteMapping("/current-user/liked")
    public void removeGoodFromLiked(@RequestBody IdRequestDto id) {
        Good good = goodService.findById(id.getId()).orElseThrow(() -> new GoodNotFoundException(id.getId()));
        User user = userService.getCurrentUserOrThrow();

        user.getLikedGoods().remove(good);
        userService.save(user);
    }

    @PutMapping("/current-user/ordered")
    public void addGoodToOrdered(@RequestBody IdRequestDto id) {
        Good good = goodService.findById(id.getId()).orElseThrow(() -> new GoodNotFoundException(id.getId()));
        User user = userService.getCurrentUserOrThrow();

        user.getOrderedGoods().add(good);
        userService.save(user);
    }

    @DeleteMapping("/current-user/ordered")
    public void removeGoodFromOrdered(@RequestBody IdRequestDto id) {
        Good good = goodService.findById(id.getId()).orElseThrow(() -> new GoodNotFoundException(id.getId()));
        User user = userService.getCurrentUserOrThrow();

        user.getOrderedGoods().remove(good);
        userService.save(user);
    }

    @PutMapping("/current-user/selected")
    public void addGoodToSelected(@RequestBody IdRequestDto id) {
        Good good = goodService.findById(id.getId()).orElseThrow(() -> new GoodNotFoundException(id.getId()));
        User user = userService.getCurrentUserOrThrow();

        user.getSelectedGoods().add(good);
        userService.save(user);
    }

    @DeleteMapping("/current-user/selected")
    public void removeGoodFromSelected(@RequestBody IdRequestDto id) {
        Good good = goodService.findById(id.getId()).orElseThrow(() -> new GoodNotFoundException(id.getId()));
        User user = userService.getCurrentUserOrThrow();

        user.getSelectedGoods().remove(good);
        userService.save(user);
    }

    @PostMapping
    public ResponseEntity<?> createGood(
            @RequestParam String title,
            @RequestParam String size,
            @RequestParam String composition,
            @RequestParam BigDecimal price,
            @RequestBody MultipartFile fileImage
    ) {
        if (fileImage == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        goodService.save(new Good()
                .setComposition(composition)
                .setPrice(price)
                .setSize(size)
                .setTitle(title),
                fileImage);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteById(@RequestBody IdRequestDto id) {
        goodService.deleteById(id.getId());
    }

}
