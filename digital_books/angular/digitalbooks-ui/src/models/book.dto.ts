export interface BookDto{
    bookId:number,
    logo:string,
    title:string,
    category:string,
    price:number,
    authorId:number,
    author:string,
    publisher:string,
    publishedDate:string,
    active:boolean,
    content?:string
}