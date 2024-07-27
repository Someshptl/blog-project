package com.myblog.service;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.ListPostDto;
import com.myblog.payload.PostDto;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);


        Post savedPost = postRepository.save(post);

        PostDto dto = mapToDto(savedPost);

        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }


    @Override
    public ListPostDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> post = all.getContent();//all.getContent() converts all the page content or Page of post to List of post.
        List<PostDto> postDtos = post.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        ListPostDto listPostDto = new ListPostDto();
        listPostDto.setPostDtoList(postDtos);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElements((int) all.getTotalElements());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setLastPage(all.isLast());
        listPostDto.setPageNumber(all.getNumber());

        //List<Post> post = postRepository.findAll();

        return listPostDto;
    }

    public PostDto getPostsById(long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post not found with id :" + id)
        );
        return mapToDto(post);
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }

}
