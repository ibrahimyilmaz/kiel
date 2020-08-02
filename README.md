![build](https://github.com/ibrahimyilmaz/kiel/workflows/build/badge.svg)
[ ![Download](https://api.bintray.com/packages/ibrahimyilmaz/kiel/kiel/images/download.svg) ](https://bintray.com/ibrahimyilmaz/kiel/kiel/_latestVersion)

## Kiel

Kiel is a `RecyclerView.Adapter` with a minimalist and convenient Kotlin DSL which provides utility on top of Android's normal `RecyclerView.Adapter`.

<img alt="kiel_icon" src="art/kiel_icon.svg" width="250">

Most of time:
- We found ourselves  repeating same boiler plate codes for `RecyclerView.Adapter`.
- We have difficulty in handling `RecyclerView.Adapter` when there are many `viewTypes`.

But now, Kiel may help us to get rid of these problems.

## Usage:

#### Basic Usage:

```kt
 val recyclerViewAdapter = adapterOf<Text> {
                register {
                    type { Text::class.java }
                    layoutResource { R.layout.adapter_message_text_item }
                    viewHolder { ::TextMessageViewHolder }
                    onViewHolderCreated<TextMessageViewHolder>{ vh->
                       //you may handle your on click listener
                       vh.itemView.setOnClickListener {

                       }
                    }
                    onViewHolderBound<Text, TextMessageViewHolder> { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                }
     }

 recyclerView.adapter = recyclerViewAdapter
 ```

#### Different View Types:

You may register different `ViewHolder`s.

```kt
 val recyclerViewAdapter = adapterOf<MessageViewState> {
                register {
                    type { Text::class.java }
                    layoutResource { R.layout.adapter_message_text_item }
                    viewHolder { ::TextMessageViewHolder }
                    onViewHolderBound<Text, TextMessageViewHolder> { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                }

                register {
                    type { Image::class.java }
                    layoutResource { R.layout.adapter_message_image_item }
                    viewHolder { ::ImageMessageViewHolder }
                    onViewHolderBound<Image, ImageMessageViewHolder> { vh, _, item ->
                        vh.messageText.text = item.text
                        vh.sentAt.text = item.sentAt

                        Glide.with(vh.messageImage)
                            .load(item.imageUrl)
                            .into(vh.messageImage)
                    }
                }
 }


recyclerView.adapter = recyclerViewAdapter
```
#### Handling Events:

As `ViewHolder` instance is accessible in:
- `onViewHolderCreated`
- `onViewHolderBound`
- `onViewHolderBoundWithPayload`


You can handle the events in the same way how you did it before.
```kt
 val recyclerViewAdapter = adapterOf<Text> {
                register {
                    type { Text::class.java }
                    layoutResource { R.layout.adapter_message_text_item }
                    viewHolder { ::TextMessageViewHolder }
                    onViewHolderCreated<TextMessageViewHolder>{ vh->
                       vh.itemView.setOnClickListener {

                       }
                       vh.messageText.addTextChangedListener{text ->

                       }
                    }
                    onViewHolderBound<Text, TextMessageViewHolder> { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                }
 }

recyclerView.adapter = recyclerViewAdapter
```


#### DiffUtil:

You may provide your custom `DiffUtil.ItemCallback` by extending `RecyclerDiffUtilCallback` with `diffUtilCallback`:

```kt
val recyclerViewAdapter = adapterOf<MessageViewState> {
                diffUtilCallback{ oldItems, newItems-> RecyclerDiffUtilCallback(oldItems,newItems)}
                register {
                    type { Text::class.java }
                    layoutResource { R.layout.adapter_message_text_item }
                    viewHolder { ::TextMessageViewHolder }
                    onViewHolderBound<Text, TextMessageViewHolder> { vh, _, it ->
                        vh.messageText.text = it.text
                        vh.sentAt.text = it.sentAt
                    }
                }

```
Download
--------

```groovy
implementation 'me.ibrahimyilmaz:kiel:latestVersion'
```

License
-------
```
Copyright 2020 Ibrahim Yilmaz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
